package com.task.bookstorewebbapp.db.dao.impl;

import com.task.bookstorewebbapp.db.DBUtils;
import com.task.bookstorewebbapp.db.SearchField;
import com.task.bookstorewebbapp.db.dao.DAO;
import com.task.bookstorewebbapp.db.entity.StatusEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class StatusDAO implements DAO<StatusEntity> {
  private static final String ID = "id";
  private static final String STATUS_NAME = "status_name";
  private static final String SELECT_ALL_STATUSES = "SELECT * FROM statuses";
  private static final Logger LOGGER = LogManager.getLogger(StatusDAO.class.getName());

  private final DBUtils connectionSupplier = DBUtils.getInstance();



  @Override
  public <V> StatusEntity getEntityByField(SearchField<V> fieldValue) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public <V> List<StatusEntity> getEntitiesByField(V fieldValue) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<StatusEntity> getEntities() throws SQLException {
    List<StatusEntity> entities = new ArrayList<>();
    try(Connection con = connectionSupplier.getConnection();
        PreparedStatement ps = con.prepareStatement(SELECT_ALL_STATUSES)) {
      ResultSet resultSet = ps.executeQuery();
      while (resultSet.next()){
        entities.add(mapToStatusEntity(resultSet));
      }
    } catch (SQLException e) {
      LOGGER.error("Can't retrieve statuses from database " + e.getMessage(), e);
      throw new SQLException("Can't retrieve statuses from database " + e.getMessage(), e);
    }
    return entities;
  }

  @Override
  public long insertEntity(StatusEntity entity) throws SQLException {
    throw new UnsupportedOperationException();  }

  @Override
  public boolean updateEntity(StatusEntity entity) throws SQLException {
    throw new UnsupportedOperationException();  }

  @Override
  public boolean deleteEntity(StatusEntity entity) throws SQLException {
    throw new UnsupportedOperationException();  }
  private StatusEntity mapToStatusEntity(ResultSet resultSet) throws SQLException {
    StatusEntity statusEntity = new StatusEntity();
    statusEntity.setId(resultSet.getLong(ID));
    statusEntity.setStatusName(resultSet.getString(STATUS_NAME));
    return statusEntity;
  }
}
