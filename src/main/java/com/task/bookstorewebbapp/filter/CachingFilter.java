package com.task.bookstorewebbapp.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CachingFilter implements Filter {

  private static final String HEADER_NAME = "Cache-Control";
  private static final String HEADER_VALUE = "private, no-store, no-cache, must-revalidate";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    ((HttpServletResponse) response).setHeader(HEADER_NAME, HEADER_VALUE);
    chain.doFilter(request, response);
  }
}
