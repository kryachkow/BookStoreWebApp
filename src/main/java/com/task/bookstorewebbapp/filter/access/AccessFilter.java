package com.task.bookstorewebbapp.filter.access;

import com.task.bookstorewebbapp.model.security.FilteringAction;
import com.task.bookstorewebbapp.model.security.FilteringAggregator;
import com.task.bookstorewebbapp.repository.securityAction.impl.SecurityActionRepositoryImpl;
import com.task.bookstorewebbapp.service.user.UserService;
import com.task.bookstorewebbapp.service.user.impl.UserServiceImpl;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@WebFilter(urlPatterns = {"/guest/*", "/protected/*", "/user/*"})
public class AccessFilter implements Filter {

  private static final String SECURITY_PARAM_PATH = "C:\\Users\\Admin\\Documents\\BookStoreWebbApp\\src\\main\\resources\\securityDescriptor.xml";

  private static final Logger LOGGER = LogManager.getLogger(AccessFilter.class.getName());
  private final UserService userService = new UserServiceImpl();

  private Map<Predicate<String>, FilteringAction<UserService>> filterMap;


  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    filterMap = new SecurityActionRepositoryImpl().getSecurityMap(SECURITY_PARAM_PATH);
    Filter.super.init(filterConfig);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpReq = (HttpServletRequest) request;
    FilteringAggregator<UserService> aggregator = new FilteringAggregator<>(httpReq,
        (HttpServletResponse) response, chain, userService);
    LOGGER.info("Access filter usage to path " + httpReq.getRequestURL().toString());
    filterMap
        .entrySet()
        .stream()
        .filter(entry -> entry.getKey().test(httpReq.getRequestURL().toString()))
        .map(Entry::getValue)
        .findAny()
        .orElse(arg -> arg.getChain().doFilter(arg.getRequest(), arg.getResponse()))
        .acceptAggregator(aggregator);
  }


}
