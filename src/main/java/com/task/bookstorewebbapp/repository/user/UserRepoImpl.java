package com.task.bookstorewebbapp.repository.user;

import com.task.bookstorewebbapp.entity.UserEntity;
import java.util.List;

public class UserRepoImpl implements UserRepository {
  List<String> CONSTANT_NICKS = List.of("Nick1", "Nick2", "Nick3");
  List<String> CONSTANT_EMAILS = List.of("email@gmail.com", "example@bing.com");

  @Override
  public UserEntity getUserByNickname(String nickname) {
    if(CONSTANT_NICKS.contains(nickname)) {
      return new UserEntity();
    }
    return null;
  }

  @Override
  public UserEntity getUserByEmail(String email) {
    if(CONSTANT_EMAILS.contains(email)) {
      return new UserEntity();
    }
    return null;
  }

  @Override
  public UserEntity getUserById(long id) {
    return null;
  }

  @Override
  public List<UserEntity> getAllUsers() {
    return null;
  }

  @Override
  public UserEntity updateUserById(long id, String email, String name, String surname,
      String nickname, String password, boolean mailingSubscription) {
    return null;
  }

  @Override
  public long addUser(String email, String name, String surname, String nickname, String password,
      boolean mailingSubscription) {
    return 1;
  }
}
