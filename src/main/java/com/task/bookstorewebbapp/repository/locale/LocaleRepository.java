package com.task.bookstorewebbapp.repository.locale;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

public interface LocaleRepository {


  Optional<Locale> getLocaleFormRequest(HttpServletRequest request);
  void setLocaleToRequest(HttpServletRequest request, HttpServletResponse response, Locale locale);


  Set<Locale> getAccessibleLocales();
  Locale getBaseLocale();

}
