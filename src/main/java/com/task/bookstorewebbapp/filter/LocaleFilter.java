package com.task.bookstorewebbapp.filter;

import com.task.bookstorewebbapp.repository.locale.LocaleRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;

@WebFilter("/*")
public class LocaleFilter implements Filter {

  private  static LocaleRepository localeRepository;




  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    Locale locale = localeRepository.getLocaleFormRequest(httpServletRequest).orElseGet(
        () -> getMostOptimalLocale(httpServletRequest)
    );
    httpServletResponse.setLocale(locale);
    localeRepository.setLocaleToRequest(httpServletRequest, httpServletResponse, locale);
    chain.doFilter(request, response);
  }

  private Locale getMostOptimalLocale(HttpServletRequest request){
    Enumeration<Locale> locales = request.getLocales();
    while (locales.hasMoreElements()) {
      Locale checkLocale = locales.nextElement();
      if(localeRepository.getAccessibleLocales().contains(checkLocale)) {
        return checkLocale;
      }
    }
    return localeRepository.getBaseLocale();
  }

  public static void setLocaleRepository(
      LocaleRepository localeRepository) {
    LocaleFilter.localeRepository = localeRepository;
  }



}
