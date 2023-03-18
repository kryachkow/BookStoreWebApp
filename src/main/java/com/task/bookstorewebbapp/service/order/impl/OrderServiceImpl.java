package com.task.bookstorewebbapp.service.order.impl;

import com.task.bookstorewebbapp.db.SearchField;
import com.task.bookstorewebbapp.db.dao.DAO;
import com.task.bookstorewebbapp.db.dao.impl.BookDAO;
import com.task.bookstorewebbapp.db.dao.impl.OrderDAO;
import com.task.bookstorewebbapp.db.dao.impl.PaymentTypeDAO;
import com.task.bookstorewebbapp.db.entity.BookEntity;
import com.task.bookstorewebbapp.db.entity.OrderEntity;
import com.task.bookstorewebbapp.db.entity.OrderPartEntity;
import com.task.bookstorewebbapp.db.entity.PaymentTypeEntity;
import com.task.bookstorewebbapp.model.Cart;
import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.service.order.OrderService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class OrderServiceImpl implements OrderService {

  private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class.getName());

  private final DAO<OrderEntity> orderDAO = new OrderDAO();
  private final DAO<BookEntity> bookDAO = new BookDAO();
  private final DAO<PaymentTypeEntity> paymentTypeEntityDAO = new PaymentTypeDAO();

  @Override
  public List<OrderPartEntity> getOrderPartsFromCart(Cart cart) {
    List<OrderPartEntity> orderParts = new ArrayList<>();
    SearchField<Long> idSearchField = new SearchField<>("books.id", -1L);
    for(Entry<Long, Integer> entry: cart.getEntries()){
      try {
        idSearchField.setValue(entry.getKey());
        BookEntity bookEntity = bookDAO.getEntityByField(idSearchField);
        orderParts.add(new OrderPartEntity(bookEntity, entry.getValue()));
      } catch (SQLException e) {
        LOGGER.warn("Couldn't obtain book by id from database" + e);
      }
    }
    return orderParts;
  }

  @Override
  public List<PaymentTypeEntity> getPaymentTypes() throws SQLException {
    return paymentTypeEntityDAO.getEntities();
  }

  @Override
  public long addOrder(Cart cart, String paymentDetails, long paymentTypeId, User user)
      throws SQLException {
    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setOrderParts(getOrderPartsFromCart(cart));
    orderEntity.setPaymentDetails(paymentDetails);
    orderEntity.setUserId(user.getId());
    orderEntity.setPaymentTypeEntity(new PaymentTypeEntity(paymentTypeId,""));
    return orderDAO.insertEntity(orderEntity);
  }


}
