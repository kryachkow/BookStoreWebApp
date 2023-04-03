package com.task.bookstorewebbapp.model.security;

import java.util.List;
import java.util.Objects;

public class SecurityParameters {

  private String urlPattern;
  private List<String> roleNames;

  public SecurityParameters() {
  }

  public SecurityParameters(String urlPattern, List<String> roleNames) {
    this.urlPattern = urlPattern;
    this.roleNames = roleNames;
  }

  public String getUrlPattern() {
    return urlPattern;
  }

  public void setUrlPattern(String urlPattern) {
    this.urlPattern = urlPattern;
  }

  public List<String> getRoleNames() {
    return roleNames;
  }

  public void setRoleNames(List<String> roleNames) {
    this.roleNames = roleNames;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SecurityParameters)) {
      return false;
    }
    SecurityParameters that = (SecurityParameters) o;
    return Objects.equals(getUrlPattern(), that.getUrlPattern())
        && Objects.equals(getRoleNames(), that.getRoleNames());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUrlPattern(), getRoleNames());
  }
}
