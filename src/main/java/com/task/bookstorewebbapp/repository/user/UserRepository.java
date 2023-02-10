package com.task.bookstorewebbapp.repository.user;

import com.task.bookstorewebbapp.entity.UserEntity;
import java.util.List;

public interface UserRepository {
  UserEntity getUserByNickname(String nickname);
  UserEntity getUserByEmail(String email);
  UserEntity getUserById(long id);
  List<UserEntity> getAllUsers();
  UserEntity updateUserById(long id, String email, String name, String surname, String nickname, String password, boolean mailingSubscription);
  long addUser(String email, String name, String surname, String nickname, String password, boolean mailingSubscription);
}
