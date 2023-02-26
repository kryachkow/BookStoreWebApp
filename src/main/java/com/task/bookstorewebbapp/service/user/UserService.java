package com.task.bookstorewebbapp.service.user;

import com.task.bookstorewebbapp.db.entity.UserEntity;
import com.task.bookstorewebbapp.db.exception.DAOException;
import java.util.List;

public interface UserService {


  public UserEntity getUserByNickname(String nickname);

  public UserEntity getUserByEmail(String email);

  public UserEntity addUser(String email, String name, String surname, String nickname,
      String password,
      boolean mailingSubscription) throws DAOException;


  public List<UserEntity> getUsers();

}
