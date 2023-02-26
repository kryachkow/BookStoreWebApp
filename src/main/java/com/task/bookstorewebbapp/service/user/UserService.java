package com.task.bookstorewebbapp.service.user;

import com.task.bookstorewebbapp.db.entity.UserEntity;
import com.task.bookstorewebbapp.db.exception.DAOException;
import java.util.List;

public interface UserService {


  UserEntity getUserByNickname(String nickname);

  UserEntity getUserByEmail(String email);

  UserEntity addUser(String email, String name, String surname, String nickname,
      String password,
      boolean mailingSubscription) throws DAOException;


  List<UserEntity> getUsers();

}
