package com.task.bookstorewebbapp.db.dao;

import com.task.bookstorewebbapp.db.DBUtils;
import com.task.bookstorewebbapp.db.SearchField;
import com.task.bookstorewebbapp.db.entity.UserEntity;
import com.task.bookstorewebbapp.db.exception.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDAO implements DAO<UserEntity> {

  private static final String ID = "id";
  private static final String EMAIl = "email";
  private static final String NAME = "name";
  private static final String SURNAME = "surname";
  private static final String NICKNAME = "nickname";
  private static final String PASSWORD = "password";
  private static final String MAILING_SUBSCRIPTION = "mailing_subscription";

  private static final String SELECT_BY_FIELD_STATEMENT = "SELECT * FROM users WHERE %s = ?";
  private static final String INSERT_USER_STATEMENT = "INSERT INTO `users`(`email`,`name`,`surname`,`nickname`,`password`,`mailing_subscription`) VALUES(?,?,?,?,?,?)";

  private final DBUtils connectionSupplier = DBUtils.getInstance();


  @Override
  public <V> UserEntity getEntityByField(SearchField<V> fieldValue) throws DAOException {
    UserEntity user;
    try (Connection con = connectionSupplier.getConnection();
        PreparedStatement ps = con.prepareStatement(
            String.format(SELECT_BY_FIELD_STATEMENT, fieldValue.getName()))) {
      ps.setObject(1, fieldValue.getValue());
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        user = mapToUserEntity(rs);
      } else {
        throw new SQLException();
      }
    } catch (SQLException e) {
      throw new DAOException("Cannot select user by Id", e);
    }
    return user;
  }

  @Override
  public <V> List<UserEntity> getEntitiesByField(V fieldValue) throws DAOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<UserEntity> getEntities() throws DAOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public long insertEntity(UserEntity entity) throws DAOException {
    long id;
    try (Connection con = connectionSupplier.getConnection();
        PreparedStatement ps = con.prepareStatement(INSERT_USER_STATEMENT,
            Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, entity.getEmail());
      ps.setString(2, entity.getName());
      ps.setString(3, entity.getSurname());
      ps.setString(4, entity.getNickname());
      ps.setString(5, entity.getPassword());
      ps.setBoolean(6, entity.isMailingSubscription());
      ps.executeUpdate();
      ResultSet generatedKeys = ps.getGeneratedKeys();
      generatedKeys.next();
      id = generatedKeys.getInt(1);
    } catch (SQLException e) {
      throw new DAOException("Cannot insert user", e);
    }
    return id;
  }

  @Override
  public boolean updateEntity(UserEntity entity) throws DAOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean deleteEntity(UserEntity entity) throws DAOException {
    throw new UnsupportedOperationException();
  }


  private UserEntity mapToUserEntity(ResultSet resultSet) throws SQLException {
    UserEntity user = new UserEntity();
    user.setId(resultSet.getInt(ID));
    user.setEmail(resultSet.getString(EMAIl));
    user.setName(resultSet.getString(NAME));
    user.setSurname(resultSet.getString(SURNAME));
    user.setNickname(resultSet.getString(NICKNAME));
    user.setPassword(resultSet.getString(PASSWORD));
    user.setMailingSubscription(resultSet.getBoolean(MAILING_SUBSCRIPTION));
    return user;
  }
}
