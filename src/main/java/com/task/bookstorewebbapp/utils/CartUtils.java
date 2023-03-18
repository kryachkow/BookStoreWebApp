package com.task.bookstorewebbapp.utils;

import com.task.bookstorewebbapp.model.Cart;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public final class CartUtils {

  public static Map<String, BiConsumer<Cart, Long>> cartOperationsMap = new HashMap<>();
  static {
    cartOperationsMap.put("decrease", (cart, bookId) -> {
          if (cart.getAmountById(bookId) > 1) {
            cart.setQuantity(bookId, cart.getAmountById(bookId) - 1);
          } else {
            cart.remove(bookId);
          }
        }
      );
    cartOperationsMap.put("increase", (cart, bookId) -> cart.setQuantity(bookId, cart.getItemQuantity() + 1));
    cartOperationsMap.put("remove", Cart::remove);
  }

  private CartUtils(){}


  public static void makeCartOperation(Cart cart, long bookId, String command) {
    cartOperationsMap.getOrDefault(command, (cartV, bookIdV) -> {}).accept(cart, bookId);
  }


  public static Cart getCart(HttpServletRequest request) {
    return (Cart) Optional.ofNullable(request.getSession().getAttribute(Constants.CART_ATTRIBUTE))
        .orElseGet(() ->
        {
          Cart cart = new Cart();
          request.getSession().setAttribute(Constants.CART_ATTRIBUTE, cart);
          return cart;
        });
  }
}
