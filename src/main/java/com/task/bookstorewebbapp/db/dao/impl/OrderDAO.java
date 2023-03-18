package com.task.bookstorewebbapp.db.dao.impl;

import com.task.bookstorewebbapp.db.DBUtils;
import com.task.bookstorewebbapp.db.SearchField;
import com.task.bookstorewebbapp.db.dao.DAO;
import com.task.bookstorewebbapp.db.entity.OrderEntity;
import com.task.bookstorewebbapp.db.entity.OrderPartEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class OrderDAO implements DAO<OrderEntity> {

  private static final String INSERT_ORDER =
      "INSERT INTO orders (status_description, status_id, user_id, payment_types_id, payment_details)"
          + "VALUES ('Order Accepted', 1, ?, ?, ?)";
  private static final String INSERT_ORDER_PART =
      "INSERT INTO order_parts (quantity, book_id, order_id)"
          + "VALUES %s;";

  private static final Logger LOGGER = LogManager.getLogger(OrderDAO.class.getName());
  private final DBUtils connectionSupplier = DBUtils.getInstance();


  @Override
  public <V> OrderEntity getEntityByField(SearchField<V> fieldValue) throws SQLException {
    throw new UnsupportedOperationException();

  }

  @Override
  public <V> List<OrderEntity> getEntitiesByField(V fieldValue) throws SQLException {
    throw new UnsupportedOperationException();

  }

  @Override
  public List<OrderEntity> getEntities() throws SQLException {
    throw new UnsupportedOperationException();

  }

  @Override
  public long insertEntity(OrderEntity entity) throws SQLException {
    long orderId = -1;
    Connection con = null;
    PreparedStatement addOrderSt = null;
    PreparedStatement addOrderPartSt = null;
    try {
      con = connectionSupplier.getConnection();
      addOrderSt = con.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
      con.setAutoCommit(false);
      addOrderSt.setLong(1, entity.getUserId());
      addOrderSt.setLong(2, entity.getPaymentTypeEntity().getId());
      addOrderSt.setString(3, entity.getPaymentDetails());
      int affectedRows = addOrderSt.executeUpdate();
      if (affectedRows > 0) {
        ResultSet resultSet = addOrderSt.getGeneratedKeys();
        resultSet.next();
        orderId =  resultSet.getLong(1);
        String orderPartValues = orderValuesPartGenerator(entity.getOrderParts(),
           orderId);
        addOrderPartSt = con.prepareStatement(String.format(INSERT_ORDER_PART, orderPartValues));
        addOrderPartSt.executeUpdate();
      }
      con.commit();
    } catch (SQLException e) {
      con.rollback();
      LOGGER.error("Cannot insert order", e);
      throw new SQLException("Cannot insert order", e);
    } finally {
      if (con != null) {
        con.close();
      }
      if (addOrderSt != null) {
        addOrderSt.close();
      }
      if (addOrderPartSt != null) {
        addOrderPartSt.close();
      }
    }
  return orderId;
  }

  @Override
  public boolean updateEntity(OrderEntity entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean deleteEntity(OrderEntity entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  private String orderValuesPartGenerator(List<OrderPartEntity> orderPartEntities, long orderId) {
    StringBuilder builder = new StringBuilder();
    orderPartEntities.forEach(orderPart -> builder.append("(")
        .append(orderPart.getQuantity()).append(",").append(orderPart.getBookEntity().getId())
        .append(",").append(orderId).append("),"));
    return builder.deleteCharAt(builder.length() - 1).toString();
  }
}
