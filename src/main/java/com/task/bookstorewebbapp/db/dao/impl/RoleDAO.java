package com.task.bookstorewebbapp.db.dao.impl;

import com.task.bookstorewebbapp.db.DBUtils;
import com.task.bookstorewebbapp.db.SearchField;
import com.task.bookstorewebbapp.db.dao.DAO;
import com.task.bookstorewebbapp.db.entity.RoleEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class RoleDAO implements DAO<RoleEntity> {

  private static final String ID = "id";
  private static final String ROLE_NAME = "name";
  private static final String SELECT_ROLE = "SELECT * FROM roles WHERE %s = ?" ;
  private static final Logger LOGGER = LogManager.getLogger(RoleDAO.class.getName());

  private final DBUtils connectionSupplier = DBUtils.getInstance();

  @Override
  public <V> RoleEntity getEntityByField(SearchField<V> fieldValue) throws SQLException {
    RoleEntity roleEntity;
    try (Connection con = connectionSupplier.getConnection();
        PreparedStatement ps = con.prepareStatement(
            String.format(SELECT_ROLE, fieldValue.getName()))) {
      ps.setObject(1, fieldValue.getValue());
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        roleEntity = mapToRoleEntity(rs);
      } else {
        throw new SQLException("There is no role with such" + fieldValue.getName());
      }
    } catch (SQLException e) {
      LOGGER.warn("Cannot select role by " + fieldValue.getName(), e);
      throw new SQLException("Cannot select role by" + fieldValue.getName(), e);
    }
    return roleEntity;
  }

  @Override
  public <V> List<RoleEntity> getEntitiesByField(V fieldValue) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<RoleEntity> getEntities() throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public long insertEntity(RoleEntity entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean updateEntity(RoleEntity entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean deleteEntity(RoleEntity entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  private RoleEntity mapToRoleEntity(ResultSet resultSet) throws SQLException {
    RoleEntity roleEntity = new RoleEntity();
    roleEntity.setId(resultSet.getLong(ID));
    roleEntity.setName(resultSet.getString(ROLE_NAME));
    return roleEntity;
  }
}
