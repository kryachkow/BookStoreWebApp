package com.task.bookstorewebbapp.model;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;

public class ValidationDTO<V> {

  private final HttpServletRequest request;
  private final UserFormDTO userFormDTO;
  private final StringBuilder errorMessage;
  private V returnValue;

  public ValidationDTO(HttpServletRequest request, UserFormDTO userFormDTO) {
    this.request = request;
    this.userFormDTO = userFormDTO;
    this.errorMessage = new StringBuilder();
  }


  public void setReturnValue(V returnValue) {
    this.returnValue = returnValue;
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public UserFormDTO getUserFormDTO() {
    return userFormDTO;
  }

  public StringBuilder getErrorMessage() {
    return errorMessage;
  }

  public boolean isErrorExists() {
    return errorMessage.length() > 0;
  }

  public V getReturnValue() {
    return returnValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ValidationDTO)) {
      return false;
    }
    ValidationDTO<?> that = (ValidationDTO<?>) o;
    return Objects.equals(getRequest(), that.getRequest()) && Objects.equals(
        getUserFormDTO(), that.getUserFormDTO()) && Objects.equals(getErrorMessage(),
        that.getErrorMessage()) && Objects.equals(getReturnValue(), that.getReturnValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getRequest(), getUserFormDTO(), getErrorMessage(), getReturnValue());
  }
}
