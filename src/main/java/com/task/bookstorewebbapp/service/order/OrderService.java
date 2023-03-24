package com.task.bookstorewebbapp.service.order;

import com.task.bookstorewebbapp.db.entity.OrderPartEntity;
import com.task.bookstorewebbapp.db.entity.PaymentTypeEntity;
import com.task.bookstorewebbapp.model.Cart;
import com.task.bookstorewebbapp.model.User;
import java.sql.SQLException;
import java.util.List;

public interface OrderService {
  List<OrderPartEntity> getOrderPartsFromCart(Cart cart);
  List<PaymentTypeEntity> getPaymentTypes() throws SQLException;

  long addOrder(Cart cart, String paymentDetails, long paymentMethodId, User user)
      throws SQLException;
}
