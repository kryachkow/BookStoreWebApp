package com.task.bookstorewebbapp.db.dao.impl;

import com.task.bookstorewebbapp.db.DBUtils;
import com.task.bookstorewebbapp.db.SearchField;
import com.task.bookstorewebbapp.db.dao.DAO;
import com.task.bookstorewebbapp.db.entity.PublisherEntity;
import com.task.bookstorewebbapp.db.exception.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PublisherDAO implements DAO<PublisherEntity> {

  private static final String ID = "id";
  private static final String PUBLISHER_NAME = "publisher";
  private static final String SELECT_ALL_PUBLISHERS = "SELECT * FROM publishers";
  private static final Logger LOGGER = LogManager.getLogger(PublisherDAO.class.getName());

  private final DBUtils connectionSupplier = DBUtils.getInstance();



  @Override
  public <V> PublisherEntity getEntityByField(SearchField<V> fieldValue) throws DAOException {
    return null;
  }

  @Override
  public <V> List<PublisherEntity> getEntitiesByField(V fieldValue) throws DAOException {
    return null;
  }

  @Override
  public List<PublisherEntity> getEntities() throws DAOException {
    List<PublisherEntity> entities = new ArrayList<>();
    try(Connection con = connectionSupplier.getConnection();
        PreparedStatement ps = con.prepareStatement(SELECT_ALL_PUBLISHERS)) {
      ResultSet resultSet = ps.executeQuery();
      while (resultSet.next()){
        entities.add(mapToPublisherEntity(resultSet));
      }
    } catch (SQLException e) {
      LOGGER.error("Can't retrieve publishers from database " + e.getMessage(), e);
      throw new DAOException("Can't retrieve publishers from database " + e.getMessage(), e);
    }
    return entities;
  }

  @Override
  public long insertEntity(PublisherEntity entity) throws DAOException {
    return 0;
  }

  @Override
  public boolean updateEntity(PublisherEntity entity) throws DAOException {
    return false;
  }

  @Override
  public boolean deleteEntity(PublisherEntity entity) throws DAOException {
    return false;
  }
  private PublisherEntity mapToPublisherEntity(ResultSet resultSet) throws SQLException {
    PublisherEntity publisherEntity = new PublisherEntity();
    publisherEntity.setId(resultSet.getLong(ID));
    publisherEntity.setName(resultSet.getString(PUBLISHER_NAME));
    return publisherEntity;
  }
}
