package com.task.bookstorewebbapp.servlet;

import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.service.order.OrderService;
import com.task.bookstorewebbapp.service.order.impl.OrderServiceImpl;
import com.task.bookstorewebbapp.utils.CartUtils;
import com.task.bookstorewebbapp.utils.Constants;
import com.task.bookstorewebbapp.utils.OrderUtils;
import com.task.bookstorewebbapp.utils.ProjectPaths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "order", value = "/user/order")
public class OrderServlet extends HttpServlet {

  OrderService orderService = new OrderServiceImpl();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doPost(req,resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (!OrderUtils.isOrderRequestValid(req)) {
      resp.sendRedirect(Constants.FOLDER_EXIT + ProjectPaths.CART_SERVLET);
      return;
    }
    try {
      orderService.addOrder(CartUtils.getCart(req), req.getParameter(Constants.PAYMENT_DETAILS),
          Long.parseLong(req.getParameter(Constants.PAYMENT_TYPE_ID)),
          (User) req.getSession().getAttribute(Constants.USER_ATTRIBUTE));
    } catch (SQLException e) {
      req.getSession().setAttribute(Constants.ERROR_ATTRIBUTE, Constants.DATABASE_ERROR);
      resp.sendRedirect(Constants.FOLDER_EXIT + ProjectPaths.CART_SERVLET);
      return;
    }
    CartUtils.getCart(req).cleanCart();
    resp.sendRedirect(Constants.FOLDER_EXIT + ProjectPaths.INDEX_JSP);
  }
}
