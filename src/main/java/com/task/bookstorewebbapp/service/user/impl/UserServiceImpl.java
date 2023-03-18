package com.task.bookstorewebbapp.service.user.impl;

import com.task.bookstorewebbapp.db.SearchField;
import com.task.bookstorewebbapp.db.dao.DAO;
import com.task.bookstorewebbapp.db.dao.impl.UserDAO;
import com.task.bookstorewebbapp.db.entity.UserEntity;
import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.service.user.UserService;
import com.task.bookstorewebbapp.utils.PasswordUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

  private static final String NICKNAME_FIELD = "nickname";
  private static final String EMAIL_FIELD = "email";
  private final DAO<UserEntity> userDAO = new UserDAO();

  @Override
  public Optional<User> getUserByNickname(String nickname) {
    User user = null;
    try {
      user = User.toModel(userDAO.getEntityByField(new SearchField<>(NICKNAME_FIELD, nickname)));
    } catch (SQLException ignored) {
    }
    return Optional.ofNullable(user);
  }

  @Override
  public Optional<User> getUserByEmail(String email) {
    User user = null;
    try {
      user = User.toModel(obtainUserEntityByEmail(email));
    } catch (SQLException ignored) {
    }
    return Optional.ofNullable(user);
  }

  @Override
  public Optional<User> addUser(String email, String name, String surname, String nickname,
      String password,
      boolean mailingSubscription) {
    Optional<User> optionalUser;
    UserEntity userEntity = new UserEntity();
    userEntity.setEmail(email);
    userEntity.setName(name);
    userEntity.setSurname(surname);
    userEntity.setNickname(nickname);
    userEntity.setPassword(PasswordUtils.encodePassword(password));
    userEntity.setMailingSubscription(mailingSubscription);
    try {
      userEntity.setId(userDAO.insertEntity(userEntity));
      optionalUser = Optional.of(User.toModel(userEntity));
    } catch (SQLException e) {
      optionalUser = Optional.empty();
    }
    return optionalUser;
  }

  @Override
  public Optional<User> authenticateUser(String email, String password) {
    Optional<User> optionalUser;
    try {
      UserEntity userEntity = obtainUserEntityByEmail(email);
      optionalUser = PasswordUtils.checkPassword(password, userEntity.getPassword()) ? Optional.of(
          User.toModel(userEntity)) : Optional.empty();
    } catch (SQLException e) {
      optionalUser = Optional.empty();
    }
    return optionalUser;
  }


  @Override
  public List<User> getUsers() {
    throw new UnsupportedOperationException();
  }

  private UserEntity obtainUserEntityByEmail(String email) throws SQLException {
    return userDAO.getEntityByField(new SearchField<>(EMAIL_FIELD, email));
  }
}
