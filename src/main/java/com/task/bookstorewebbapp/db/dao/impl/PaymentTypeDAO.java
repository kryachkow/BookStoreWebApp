package com.task.bookstorewebbapp.db.dao.impl;

import com.task.bookstorewebbapp.db.DBUtils;
import com.task.bookstorewebbapp.db.SearchField;
import com.task.bookstorewebbapp.db.dao.DAO;
import com.task.bookstorewebbapp.db.entity.PaymentTypeEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PaymentTypeDAO implements DAO<PaymentTypeEntity> {
  private static final String ID = "id";
  private static final String PAYMENT_TYPE = "payment_type";
  private static final String SELECT_ALL_PAYMENT_TYPES = "SELECT * FROM payment_types";
  private static final Logger LOGGER = LogManager.getLogger(PaymentTypeDAO.class.getName());

  private final DBUtils connectionSupplier = DBUtils.getInstance();



  @Override
  public <V> PaymentTypeEntity getEntityByField(SearchField<V> fieldValue) throws SQLException {
    throw new UnsupportedOperationException();

  }

  @Override
  public <V> List<PaymentTypeEntity> getEntitiesByField(V fieldValue) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<PaymentTypeEntity> getEntities() throws SQLException {
    List<PaymentTypeEntity> entities = new ArrayList<>();
    try(Connection con = connectionSupplier.getConnection();
        PreparedStatement ps = con.prepareStatement(SELECT_ALL_PAYMENT_TYPES)) {
      ResultSet resultSet = ps.executeQuery();
      while (resultSet.next()){
        entities.add(mapToPaymentTypeEntity(resultSet));
      }
    } catch (SQLException e) {
      LOGGER.error("Can't retrieve Payment Types from database " + e.getMessage(), e);
      throw new SQLException("Can't retrieve Payment Types from database " + e.getMessage(), e);
    }
    return entities;
  }

  @Override
  public long insertEntity(PaymentTypeEntity entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean updateEntity(PaymentTypeEntity entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean deleteEntity(PaymentTypeEntity entity) throws SQLException {
    throw new UnsupportedOperationException();
  }
  private PaymentTypeEntity mapToPaymentTypeEntity(ResultSet resultSet) throws SQLException {
    PaymentTypeEntity paymentTypeEntity = new PaymentTypeEntity();
    paymentTypeEntity.setId(resultSet.getLong(ID));
    paymentTypeEntity.setPaymentType(resultSet.getString(PAYMENT_TYPE));
    return paymentTypeEntity;
  }
}
