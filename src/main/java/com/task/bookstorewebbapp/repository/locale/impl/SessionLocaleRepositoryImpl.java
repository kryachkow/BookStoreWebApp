package com.task.bookstorewebbapp.repository.locale.impl;

import com.task.bookstorewebbapp.repository.locale.LocaleRepository;
import com.task.bookstorewebbapp.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public class SessionLocaleRepositoryImpl implements LocaleRepository {

  private final Set<Locale> locales = new HashSet<>();
  private final Locale baseLocale;

  public SessionLocaleRepositoryImpl(Properties languages, Locale baseLocale) {
    languages.forEach((key, value) ->
        locales.add(Locale.forLanguageTag((String) key)));
    this.baseLocale = baseLocale;
  }

  @Override
  public Optional<Locale> getLocaleFormRequest(HttpServletRequest request) {
    String langParam = request.getParameter(Constants.LANG_PARAMETER);
    if(langParam != null) {

      return Optional.of(Locale.forLanguageTag(langParam));
    }
    return Optional.ofNullable(
        (Locale) request.getSession().getAttribute(Constants.LOCALE_ATTRIBUTE));
  }

  @Override
  public void setLocaleToRequest(HttpServletRequest request, HttpServletResponse response,
      Locale locale) {
    request.setAttribute(Constants.LOCALE_ATTRIBUTE, locale);
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

