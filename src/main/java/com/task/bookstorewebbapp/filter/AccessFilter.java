package com.task.bookstorewebbapp.filter;

import com.task.bookstorewebbapp.model.security.FilteringAction;
import com.task.bookstorewebbapp.model.security.FilteringAggregator;
import com.task.bookstorewebbapp.service.user.UserService;
import com.task.bookstorewebbapp.service.user.impl.UserServiceImpl;
import com.task.bookstorewebbapp.utils.SecurityParser;
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

@WebFilter(urlPatterns = {"/user/*", "/protected/*", "/admin/*"})
public class AccessFilter implements Filter {

  private static Map<Predicate<String>, FilteringAction<UserService>> filterMap;
  private static final String SECURITY_PARAM_PATH = "C:\\Users\\Admin\\Documents\\BookStoreWebbApp\\src\\main\\resources\\securityDescriptor.xml";
  private final UserService userService = new UserServiceImpl();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    filterMap = SecurityParser.getSecurityMap(SECURITY_PARAM_PATH);
    Filter.super.init(filterConfig);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpReq = (HttpServletRequest) request;
    FilteringAggregator<UserService> aggregator = new FilteringAggregator<>(httpReq,
        (HttpServletResponse) response, chain, userService);
    filterMap
        .entrySet()
        .stream()
        .dropWhile(entry -> entry.getKey().test(httpReq.getRequestURL().toString()))
        .map(Entry::getValue).findAny()
        .orElse(arg -> arg.getChain().doFilter(arg.getRequest(), arg.getResponse())).acceptAggregator(aggregator);
  }


}
