package com.task.bookstorewebbapp.service.user;

import com.task.bookstorewebbapp.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

  String getRoleNameByUserId(long id);

  Optional<User> getUserByNickname(String nickname);

  Optional<User> getUserByEmail(String email);

  Optional<User> addUser(String email, String name, String surname, String nickname,
      String password,
      boolean mailingSubscription);

  boolean passwordCheck(User user, String password);

  boolean isBanned(User user, boolean passwordCheck);
  List<User> getUsers();

}
