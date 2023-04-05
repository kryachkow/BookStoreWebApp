package com.task.bookstorewebbapp.repository.securityAction.impl;

import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.model.security.FilteringAction;
import com.task.bookstorewebbapp.model.security.FilteringAggregator;
import com.task.bookstorewebbapp.model.security.SecurityParameters;
import com.task.bookstorewebbapp.repository.securityAction.SecurityActionRepository;
import com.task.bookstorewebbapp.service.user.UserService;
import com.task.bookstorewebbapp.utils.Constants;
import com.task.bookstorewebbapp.utils.ProjectPaths;
import com.task.bookstorewebbapp.utils.SecurityParser;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;

public class SecurityActionRepositoryImpl implements SecurityActionRepository {

  private static final String USER = "user";
  private static final String NO_ROLE = "noRole";
  private static final String urlParam = "?goBackTo=";
  private static final String ADMIN = "admin";

  private final Map<Predicate<SecurityParameters>, FilteringAction<UserService>> rolesActionMap = new LinkedHashMap<>();

  public SecurityActionRepositoryImpl() {
    rolesActionMap.put(securityParameters -> securityParameters.getRoleNames().contains(NO_ROLE),
        SecurityActionRepositoryImpl::guestPageAction);
    rolesActionMap.put(securityParameters -> securityParameters.getRoleNames().contains(ADMIN)
            && securityParameters.getRoleNames().size() == 1,
        SecurityActionRepositoryImpl::adminPageAction);
    rolesActionMap.put(securityParameters -> securityParameters.getRoleNames().contains(USER),
        SecurityActionRepositoryImpl::loggedUserPageAction);
  }

  @Override
  public Map<Predicate<String>, FilteringAction<UserService>> getSecurityMap(
      String resource) {
    Map<Predicate<String>, FilteringAction<UserService>> securityMap = new HashMap<>();
    for (SecurityParameters parameters : SecurityParser.getSecurityParameters(resource)) {
      securityMap.put(path -> path.contains(parameters.getUrlPattern()),
          getActionByParams(parameters));
    }
    return securityMap;
  }

  private FilteringAction<UserService> getActionByParams(
      SecurityParameters securityParameters) {
    return rolesActionMap
        .entrySet()
        .stream()
        .filter(entry -> entry.getKey().test(securityParameters))
        .map(Entry::getValue)
        .findAny()
        .orElse(aggregator -> aggregator.getChain()
            .doFilter(aggregator.getRequest(), aggregator.getResponse()));
  }


  private static void adminPageAction(FilteringAggregator<UserService> aggregator)
      throws ServletException, IOException {

    User user = (User) aggregator.getRequest().getSession().getAttribute(Constants.USER_ATTRIBUTE);
    if (user == null ||
        !Objects.equals(aggregator.getUserService().getRoleNameByUserId(user.getId()), ADMIN)) {

      aggregator.getResponse().sendRedirect(Constants.FOLDER_EXIT + ProjectPaths.ERROR_403);
      return;
    }
    aggregator.getChain().doFilter(aggregator.getRequest(), aggregator.getResponse());
  }


  private static void loggedUserPageAction(FilteringAggregator<UserService> aggregator)
      throws ServletException, IOException {
    if (aggregator.getRequest().getSession().getAttribute(Constants.USER_ATTRIBUTE) == null) {
      aggregator.getResponse().sendRedirect(
          Constants.FOLDER_EXIT + ProjectPaths.SIGN_IN_SERVLET + urlParam + aggregator.getRequest()
              .getRequestURL().toString());
      return;
    }
    aggregator.getChain().doFilter(aggregator.getRequest(), aggregator.getResponse());
  }


  private static void guestPageAction(FilteringAggregator<UserService> aggregator)
      throws ServletException, IOException {
    if (aggregator.getRequest().getSession().getAttribute(Constants.USER_ATTRIBUTE) == null) {
      aggregator.getChain().doFilter(aggregator.getRequest(), aggregator.getResponse());
      return;
    }
    aggregator.getResponse().sendRedirect(Constants.FOLDER_EXIT + ProjectPaths.INDEX_JSP);
  }

}
