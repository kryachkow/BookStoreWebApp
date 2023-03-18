package com.task.bookstorewebbapp.db.dao.impl;

import com.task.bookstorewebbapp.db.DBUtils;
import com.task.bookstorewebbapp.db.SearchField;
import com.task.bookstorewebbapp.db.dao.DAO;
import com.task.bookstorewebbapp.db.entity.CategoryEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CategoryDAO implements DAO<CategoryEntity> {
  private static final String ID = "id";
  private static final String CATEGORY_NAME = "category";
  private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM categories";
  private static final Logger LOGGER = LogManager.getLogger(CategoryDAO.class.getName());

  private final DBUtils connectionSupplier = DBUtils.getInstance();

  @Override
  public <V> CategoryEntity getEntityByField(SearchField<V> fieldValue) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public <V> List<CategoryEntity> getEntitiesByField(V fieldValue) throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<CategoryEntity> getEntities() throws SQLException {
    List<CategoryEntity> entities = new ArrayList<>();
    try(Connection con = connectionSupplier.getConnection();
        PreparedStatement ps = con.prepareStatement(SELECT_ALL_CATEGORIES)) {
      ResultSet resultSet = ps.executeQuery();
      while (resultSet.next()){
        entities.add(mapToCategoryEntity(resultSet));
      }
    } catch (SQLException e) {
      LOGGER.error("Can't retrieve categories from database " + e.getMessage(), e);
      throw new SQLException("Can't retrieve categories from database " + e.getMessage(), e);
    }
    return entities;
  }

  @Override
  public long insertEntity(CategoryEntity entity) throws SQLException {
    throw new UnsupportedOperationException();

  }

  @Override
  public boolean updateEntity(CategoryEntity entity) throws SQLException {
    throw new UnsupportedOperationException();

  }

  @Override
  public boolean deleteEntity(CategoryEntity entity) throws SQLException {
    throw new UnsupportedOperationException();

  }
  private CategoryEntity mapToCategoryEntity(ResultSet resultSet) throws SQLException {
    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setId(resultSet.getLong(ID));
    categoryEntity.setName(resultSet.getString(CATEGORY_NAME));
    return categoryEntity;
  }
}
