package com.task.bookstorewebbapp.servlet;

import com.task.bookstorewebbapp.db.entity.OrderPartEntity;
import com.task.bookstorewebbapp.model.Cart;
import com.task.bookstorewebbapp.service.order.OrderService;
import com.task.bookstorewebbapp.service.order.impl.OrderServiceImpl;
import com.task.bookstorewebbapp.utils.CartUtils;
import com.task.bookstorewebbapp.utils.Constants;
import com.task.bookstorewebbapp.utils.ProjectPaths;
import com.task.bookstorewebbapp.utils.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "cart", value = "/cart")
public class CartServlet extends HttpServlet {

  private static final String COMMAND_PARAMETER = "command";
  private static final String BOOK_ID_PARAMETER = "bookId";
  private static final String ORDER_PARTS_ATTRIBUTE = "orderParts";
  private static final String TOTAL_PRICE_ATTRIBUTE = "totalPrice";
  private static final String PAYMENT_TYPES = "paymentTypes";



  private final OrderService orderService = new OrderServiceImpl();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    ServletUtils.sessionAttributesToRequest(req,List.of(Constants.ERROR_ATTRIBUTE));
    Cart cart = CartUtils.getCart(req);
    if (req.getParameter(COMMAND_PARAMETER) != null) {
      CartUtils.makeCartOperation(cart, Long.parseLong(req.getParameter(BOOK_ID_PARAMETER)),
          req.getParameter(COMMAND_PARAMETER));
    }
    List<OrderPartEntity> orderPartsFromCart = orderService.getOrderPartsFromCart(cart);
    req.setAttribute(ORDER_PARTS_ATTRIBUTE, orderPartsFromCart);
    int totalPrice = orderPartsFromCart.stream().map(
        orderPartEntity -> orderPartEntity.getQuantity() * orderPartEntity.getBookEntity()
            .getPrice()).reduce(0, Integer::sum);
    try {
      req.setAttribute(PAYMENT_TYPES, orderService.getPaymentTypes());
    } catch (SQLException e) {
      req.getSession().setAttribute(Constants.ERROR_ATTRIBUTE, Constants.DATABASE_ERROR);
    }
    req.setAttribute(TOTAL_PRICE_ATTRIBUTE, totalPrice);
    req.getRequestDispatcher(ProjectPaths.CART_JSP).forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req, resp);
  }
}
