package com.task.bookstorewebbapp.utils;


import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.model.security.FilteringAction;
import com.task.bookstorewebbapp.model.security.FilteringAggregator;
import com.task.bookstorewebbapp.model.security.SecurityParameters;
import com.task.bookstorewebbapp.service.user.UserService;
import jakarta.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public final class SecurityParser {

  private static final String urlParam = "?goBackTo=";
  private static final String ADMIN = "admin";
  private static final String CONSTRAINT = "constraint";
  private static final String URL_PATTERN = "url-pattern";
  private static final String ROLE = "role";
  private static final String USER = "user";

  private static final String NO_ROLE = "noRole";
  private static final Map<Predicate<SecurityParameters>, FilteringAction<UserService>> rolesActionMap = new HashMap<>();

  static {
    rolesActionMap.put(securityParameters -> securityParameters.getRoleNames().contains(NO_ROLE),
        SecurityParser::guestPageAction);
    rolesActionMap.put(securityParameters -> securityParameters.getRoleNames().contains(ADMIN)
        && securityParameters.getRoleNames().size() == 1, SecurityParser::adminPageAction);
    rolesActionMap.put(securityParameters -> securityParameters.getRoleNames().contains(USER),
        SecurityParser::loggedUserPageAction);
  }


  private SecurityParser() {
  }


  public static Map<Predicate<String>, FilteringAction<UserService>> getSecurityMap(
      String resource) {
    Map<Predicate<String>, FilteringAction<UserService>> securityMap = new HashMap<>();
    for (SecurityParameters parameters : getSecurityParameters(resource)) {
      securityMap.put(path -> path.matches(parameters.getUrlPattern()),
          getActionByParams(parameters));
    }
    return securityMap;
  }


  private static FilteringAction<UserService> getActionByParams(
      SecurityParameters securityParameters) {
    return rolesActionMap
        .entrySet()
        .stream()
        .dropWhile(entry -> entry.getKey().test(securityParameters))
        .map(Entry::getValue)
        .findAny()
        .orElse(aggregator -> aggregator.getChain()
            .doFilter(aggregator.getRequest(), aggregator.getResponse()));
  }


  private static List<SecurityParameters> getSecurityParameters(String resource) {
    List<SecurityParameters> securityParameters = new ArrayList<>();
    try {
      Document jdom = buildDocument(resource);
      Element root = jdom.getRootElement();
      for (Element constraintElement : root.getChildren(CONSTRAINT)) {
        securityParameters.add(getParamsFromElement(constraintElement));
      }
    } catch (IOException | JDOMException e) {
      throw new RuntimeException(e);
    }
    return securityParameters;
  }

  private static Document buildDocument(String resource)
      throws JDOMException, IOException {
    SAXBuilder saxBuilder = new SAXBuilder();
    return saxBuilder.build(new File(resource));
  }

  private static SecurityParameters getParamsFromElement(Element element) {
    SecurityParameters securityParameters = new SecurityParameters();
    securityParameters.setUrlPattern(element.getChildText(URL_PATTERN));
    List<String> roleList = new ArrayList<>();
    for (Element roleElems : element.getChildren(ROLE)) {
      roleList.add(roleElems.getText());
    }
    securityParameters.setRoleNames(roleList);
    return securityParameters;
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
    if (aggregator.getRequest().getSession().getAttribute(Constants.USER_ATTRIBUTE) != null) {
      aggregator.getResponse().sendRedirect(Constants.FOLDER_EXIT + ProjectPaths.INDEX_JSP);
      return;
    }
    aggregator.getChain().doFilter(aggregator.getRequest(), aggregator.getResponse());
  }


}
