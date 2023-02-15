package com.task.bookstorewebbapp.service.user;

import com.task.bookstorewebbapp.entity.UserEntity;
import java.util.List;

public interface UserService {


  public UserEntity getUserByNickname(String nickname);

  public UserEntity getUserByEmail(String email);

  public UserEntity addUser(String email, String name, String surname, String nickname,
      String password,
      boolean mailingSubscription);

  public boolean banUser(long id);

  public List<UserEntity> getUsers();

}
