package com.task.bookstorewebbapp.utils;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public final class OrderUtils {
  private static final String CART_IS_EMPTY = "Cart is empty!";
  private static final String UNAUTHENTICATED_USER = "Please log in!";
  private static final String INVALID_PAYMENT_TYPE = "Invalid payment type!";
  private static final String INVALID_PAYMENT_DETAILS = "Invalid payment details!";

  private final static Map<Predicate<HttpServletRequest>, String> orderValidationMap = new HashMap<>();
  static {
    orderValidationMap.put((request -> CartUtils.getCart(request).isEmpty()), CART_IS_EMPTY);
    orderValidationMap.put((request -> request.getSession().getAttribute(Constants.USER_ATTRIBUTE) == null), UNAUTHENTICATED_USER);
    orderValidationMap.put((request -> request.getParameter(Constants.PAYMENT_TYPE_ID)== null || Integer.parseInt(request.getParameter(Constants.PAYMENT_TYPE_ID)) < 1), INVALID_PAYMENT_TYPE);
    orderValidationMap.put((request -> request.getParameter(Constants.PAYMENT_DETAILS) == null), INVALID_PAYMENT_DETAILS);
  }
  private OrderUtils(){}

  public static boolean isOrderRequestValid(HttpServletRequest request) {
    StringBuilder builder = new StringBuilder();
    orderValidationMap.forEach((key, value) -> {
      if (key.test(request))
        builder.append(value).append(" ");
    });
    if(!builder.isEmpty()){
      request.getSession().setAttribute(Constants.ERROR_ATTRIBUTE,builder.toString().trim());
      return false;
    }
    return true;
  }

}
