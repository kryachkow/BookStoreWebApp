package com.task.bookstorewebbapp.servlet;

import com.task.bookstorewebbapp.model.Cart;
import com.task.bookstorewebbapp.utils.CartUtils;
import com.task.bookstorewebbapp.utils.Constants;
import com.task.bookstorewebbapp.utils.ProjectPaths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "addToCart", value = "/addToCart")
public class AddToCartServlet extends HttpServlet {

  private static final String RETURN_TO_CATALOG_FORMAT = "catalog?categoryId=%s&publisherId=%s&minPrice=%s&maxPrice=%s&pageNumber=%s&pageSize=%s&sorting=%s&inverted=%s%s";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getParameterMap().size() < Constants.MIN_ADD_TO_CART_PARAMS) {
      resp.sendRedirect(ProjectPaths.CATALOG_JSP);
      return;
    }
    Cart cart = CartUtils.getCart(req);
    cart.addToCart(Long.parseLong(req.getParameter(Constants.BOOK_ID)),
        Integer.parseInt(req.getParameter(Constants.QUANTITY)));
    resp.sendRedirect(generateRedirectPath(req));
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req, resp);
  }

  private String generateRedirectPath(HttpServletRequest request) {
    String titleSearch = Optional.ofNullable(request.getParameter(Constants.TITLE_SEARCH))
        .orElse("");
    return String.format(RETURN_TO_CATALOG_FORMAT, request.getParameter(Constants.CATEGORY_ID),
        request.getParameter(Constants.PUBLISHER_ID), request.getParameter(Constants.MINIMUM_PRICE),
        request.getParameter(Constants.MAXIMUM_PRICE), request.getParameter(Constants.PAGE_NUMBER),
        request.getParameter(Constants.PAGE_SIZE), request.getParameter(Constants.SORTING),
        request.getParameter(Constants.INVERTED), titleSearch);
  }

}
