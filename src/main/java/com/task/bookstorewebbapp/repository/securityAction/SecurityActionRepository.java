package com.task.bookstorewebbapp.repository.securityAction;

import com.task.bookstorewebbapp.model.security.FilteringAction;
import com.task.bookstorewebbapp.service.user.UserService;
import java.util.Map;
import java.util.function.Predicate;

public interface SecurityActionRepository {

  Map<Predicate<String>, FilteringAction<UserService>> getSecurityMap(
      String resource);
}
