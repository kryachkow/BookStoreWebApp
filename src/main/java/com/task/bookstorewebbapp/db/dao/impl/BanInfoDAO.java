package com.task.bookstorewebbapp.db.dao.impl;

import com.task.bookstorewebbapp.db.DBUtils;
import com.task.bookstorewebbapp.db.SearchField;
import com.task.bookstorewebbapp.db.dao.DAO;
import com.task.bookstorewebbapp.db.entity.BanInfoEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BanInfoDAO implements DAO<BanInfoEntity> {

  private static final String ID_FIELD = "id";
  private static final String TIMES_FIELD = "times";
  private static final String UNBLOCK_DATE = "unblock_date";
  private static final String INSERT_BAN_INFO = "INSERT INTO user_block (id) VALUES (?)";
  private static final String SELECT_BY_FIELD_STATEMENT = "SELECT * FROM user_block WHERE %s = ?";
  private static final String UPDATE_USER_BAN = "UPDATE user_block SET times = ?, unblock_date = ? WHERE id = ?";

  private static final Logger LOGGER = LogManager.getLogger(BanInfoDAO.class.getName());
  private final DBUtils connectionSupplier = DBUtils.getInstance();

  @Override
  public <V> BanInfoEntity getEntityByField(SearchField<V> fieldValue) throws SQLException {
    BanInfoEntity banInfoEntity;
    try (Connection con = connectionSupplier.getConnection();
        PreparedStatement ps = con.prepareStatement(
            String.format(SELECT_BY_FIELD_STATEMENT, fieldValue.getName()))) {
      ps.setObject(1, fieldValue.getValue());
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        banInfoEntity = mapToBanInfo(rs);
      } else {
        throw new SQLException("There is no ban info with such" + fieldValue.getName());
      }
    } catch (SQLException e) {
      LOGGER.warn("Cannot select ban info by " + fieldValue.getName(), e);
      throw new SQLException("Cannot select ban info by" + fieldValue.getName(), e);
    }
    return banInfoEntity;
  }

  @Override
  public <V> List<BanInfoEntity> getEntitiesByField(V fieldValue) throws SQLException {
    throw new UnsupportedOperationException();
    }

  @Override
  public List<BanInfoEntity> getEntities() throws SQLException {
    throw new UnsupportedOperationException();
  }

  @Override
  public long insertEntity(BanInfoEntity entity) throws SQLException {
    try (Connection con = connectionSupplier.getConnection();
        PreparedStatement ps = con.prepareStatement(INSERT_BAN_INFO,
            Statement.RETURN_GENERATED_KEYS)) {
      ps.setLong(1, entity.getUserId());
      ps.executeUpdate();
    } catch (SQLException e) {
      LOGGER.error("Cannot insert ban info ", e);
      throw new SQLException("Cannot insert ban info", e);
    }
    return entity.getUserId();
  }

  @Override
  public boolean updateEntity(BanInfoEntity entity) throws SQLException {
    try(Connection con = DBUtils.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(UPDATE_USER_BAN)) {
      ps.setInt(1, entity.getLogCount());
      if(entity.getDateTime() != null) {
        ps.setTimestamp(2, Timestamp.valueOf(entity.getDateTime()));
      } else {
        ps.setTimestamp(2, null);
      }
      ps.setLong(3, entity.getUserId());
      ps.executeUpdate();
    } catch (SQLException e) {
      LOGGER.error("Cannot update ban info", e);
      throw new SQLException("Cannot update ban info", e);
    }
    return true;
  }

  @Override
  public boolean deleteEntity(BanInfoEntity entity) throws SQLException {
    throw new UnsupportedOperationException();
  }

  private BanInfoEntity mapToBanInfo(ResultSet resultSet) throws SQLException {
    BanInfoEntity banInfoEntity = new BanInfoEntity();
    banInfoEntity.setUserId(resultSet.getLong(ID_FIELD));
    banInfoEntity.setLogCount(resultSet.getInt(TIMES_FIELD));
    Optional.ofNullable(resultSet.getTimestamp(UNBLOCK_DATE)).ifPresent(
        (timestamp -> banInfoEntity.setDateTime(timestamp.toLocalDateTime()))
    );
    return banInfoEntity;
  }
}
