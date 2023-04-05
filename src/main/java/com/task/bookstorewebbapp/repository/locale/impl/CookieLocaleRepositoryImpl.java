package com.task.bookstorewebbapp.repository.locale.impl;

import com.task.bookstorewebbapp.repository.locale.LocaleRepository;
import com.task.bookstorewebbapp.utils.Constants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public class CookieLocaleRepositoryImpl implements LocaleRepository {

  private final Set<Locale> locales = new HashSet<>();
  private final Locale baseLocale;

  private final int cookieAge;

  public CookieLocaleRepositoryImpl(Properties languages, Locale baseLocale, int maxAge) {
    cookieAge = maxAge;
    languages.forEach((key, value) ->
        locales.add(Locale.forLanguageTag((String) key)));
    this.baseLocale = baseLocale;
  }

  @Override
  public Optional<Locale> getLocaleFormRequest(HttpServletRequest request) {
    Locale locale = null;
    String langParam = request.getParameter(Constants.LANG_PARAMETER);
    if (langParam != null) {
      return Optional.of(Locale.forLanguageTag(langParam));
    }
    if (request.getCookies().length == 0) {
      return Optional.empty();
    }
    for (Cookie cookie : request.getCookies()) {
      if (cookie.getName().equals(Constants.LOCALE_ATTRIBUTE)) {
        locale = Locale.forLanguageTag(cookie.getValue());
        cookie.setMaxAge(cookieAge);
      }
    }
    return Optional.ofNullable(locale);
  }

  @Override
  public void setLocaleToRequest(HttpServletRequest request, HttpServletResponse response,
      Locale locale) {
    Cookie cookie = new Cookie(Constants.LOCALE_ATTRIBUTE, locale.getLanguage());
    cookie.setMaxAge(cookieAge);
    response.addCookie(cookie);
  }

  @Override
  public Set<Locale> getAccessibleLocales() {
    return locales;
  }

  @Override
  public Locale getBaseLocale() {
    return baseLocale;
  }


}
