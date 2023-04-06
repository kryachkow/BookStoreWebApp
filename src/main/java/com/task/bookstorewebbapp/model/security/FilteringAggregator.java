package com.task.bookstorewebbapp.model.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;

public class FilteringAggregator<V> {

  private final HttpServletRequest request;
  private final HttpServletResponse response;
  private final FilterChain chain;
  private final V service;

  public FilteringAggregator(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, V service) {
    this.request = request;
    this.response = response;
    this.chain = chain;
    this.service = service;
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public HttpServletResponse getResponse() {
    return response;
  }

  public FilterChain getChain() {
    return chain;
  }

  public V getUserService() {
    return service;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FilteringAggregator)) {
      return false;
    }
    FilteringAggregator that = (FilteringAggregator) o;
    return Objects.equals(getRequest(), that.getRequest()) && Objects.equals(
        getResponse(), that.getResponse()) && Objects.equals(getChain(), that.getChain())
        && Objects.equals(getUserService(), that.getUserService());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getRequest(), getResponse(), getChain(), getUserService());
  }
}
