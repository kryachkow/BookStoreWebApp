package com.task.bookstorewebbapp.service.user;

import com.task.bookstorewebbapp.entity.UserEntity;
import com.task.bookstorewebbapp.repository.user.UserRepoImpl;
import com.task.bookstorewebbapp.repository.user.UserRepository;
import java.util.List;

public class UserServiceImpl implements UserService {

  private final UserRepository repository = new UserRepoImpl();

  @Override
  public UserEntity getUserByNickname(String nickname) {
    return repository.getUserByNickname(nickname);
  }

  @Override
  public UserEntity getUserByEmail(String email) {
    return repository.getUserByEmail(email);
  }

  @Override
  public UserEntity addUser(String email, String name, String surname, String nickname,
      String password,
      boolean mailingSubscription) {
    long id = repository.addUser(email, name, surname, nickname, password, mailingSubscription);
    if (id >= 0) {
      return new UserEntity(id, email, name, surname, nickname, password, mailingSubscription);
    }
    return null;
  }

  @Override
  public boolean banUser(long id) {
    return false;
  }

  @Override
  public List<UserEntity> getUsers() {
    return null;
  }

}
