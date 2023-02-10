package com.task.bookstorewebbapp.service;

import com.task.bookstorewebbapp.entity.UserEntity;
import com.task.bookstorewebbapp.repository.user.UserRepoImpl;
import com.task.bookstorewebbapp.repository.user.UserRepository;

public class UserService {

  private static final UserRepository repository = new UserRepoImpl();

  public static UserEntity getUserByNickname(String nickname){
    return repository.getUserByNickname(nickname);
  }

  public static UserEntity getUserByEmail(String email){
    return repository.getUserByEmail(email);
  }

  public static UserEntity addUser(String email, String name, String surname, String nickname, String password,
      boolean mailingSubscription){
    long id = repository.addUser(email, name, surname, nickname, password, mailingSubscription);
    if(id >= 0){
      return new UserEntity(id, email, name, surname, nickname, password, mailingSubscription);
    }
    return null;
  }

}
