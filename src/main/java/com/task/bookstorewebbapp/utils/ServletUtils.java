package com.task.bookstorewebbapp.utils;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public final class ServletUtils {

  private ServletUtils() {
  }

  public static void sessionAttributesToRequest(HttpServletRequest request,
      List<String> attributes) {
    attributes.forEach(
        attribute -> {
          request.setAttribute(attribute, request.getSession().getAttribute(attribute));
          request.getSession().removeAttribute(attribute);
        }
    );

  }

}
