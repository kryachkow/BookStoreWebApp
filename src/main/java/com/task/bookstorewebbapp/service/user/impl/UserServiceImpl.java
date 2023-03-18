package com.task.bookstorewebbapp.service.user.impl;

import com.task.bookstorewebbapp.db.SearchField;
import com.task.bookstorewebbapp.db.dao.DAO;
import com.task.bookstorewebbapp.db.dao.impl.UserDAO;
import com.task.bookstorewebbapp.db.entity.UserEntity;
import com.task.bookstorewebbapp.db.exception.DAOException;
import com.task.bookstorewebbapp.service.user.UserService;
import com.task.bookstorewebbapp.utils.PasswordUtils;
import java.util.List;

public class UserServiceImpl implements UserService {

  private static final String NICKNAME_FIELD = "nickname";
  private static final String EMAIL_FIELD = "email";

  private final DAO<UserEntity> userDAO = new UserDAO();

  @Override
  public UserEntity getUserByNickname(String nickname) {
    try {
      return userDAO.getEntityByField(new SearchField<>(NICKNAME_FIELD, nickname));
    } catch (DAOException e) {
      return null;
    }
  }

  @Override
  public UserEntity getUserByEmail(String email) {
    try {
      return userDAO.getEntityByField(new SearchField<>(EMAIL_FIELD, email));
    } catch (DAOException e) {
      return null;
    }
  }

  @Override
  public UserEntity addUser(String email, String name, String surname, String nickname,
      String password,
      boolean mailingSubscription) throws DAOException {
    UserEntity userEntity = new UserEntity();
    userEntity.setEmail(email);
    userEntity.setName(name);
    userEntity.setSurname(surname);
    userEntity.setNickname(nickname);
    userEntity.setPassword(PasswordUtils.encodePassword(password));
    userEntity.setMailingSubscription(mailingSubscription);
    userEntity.setId(userDAO.insertEntity(userEntity));
    return userEntity;
  }


  @Override
  public List<UserEntity> getUsers() {
    return null;
  }

}
