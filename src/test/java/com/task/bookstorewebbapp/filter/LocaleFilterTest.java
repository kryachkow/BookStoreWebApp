package com.task.bookstorewebbapp.filter;

import com.task.bookstorewebbapp.repository.locale.LocaleRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LocaleFilterTest {

  private final LocaleFilter localeFilter = new LocaleFilter();

  @Mock
  HttpServletRequest request;

  @Mock
  HttpServletResponse response;

  @Mock
  private FilterChain filterChain;

  @Mock
  private static LocaleRepository localeRepository;



  @ParameterizedTest
  @MethodSource("getTestArguments")
  void doFilter(Locale repoRetValue, List<Locale> list, String result)
      throws ServletException, IOException {
    LocaleFilter.setLocaleRepository(localeRepository);
    Mockito.lenient().when(localeRepository.getAccessibleLocales()).thenReturn(Set.of(Locale.forLanguageTag("uk"), Locale.forLanguageTag("en")));
    Mockito.when(localeRepository.getLocaleFormRequest(request)).thenReturn(
        Optional.ofNullable(repoRetValue));
    Mockito.lenient().when(localeRepository.getBaseLocale()).thenReturn(Locale.forLanguageTag("uk"));
    Mockito.lenient().when(request.getLocales()).thenReturn(new Vector<>(list).elements());
    localeFilter.doFilter(request, response, filterChain);
    Mockito.verify(localeRepository, Mockito.times(1)).setLocaleToRequest(request, response, Locale.forLanguageTag(result));
    Mockito.verify(response, Mockito.times(1)).setLocale(Locale.forLanguageTag(result));
  }

  private static Stream<Arguments> getTestArguments() {
    return Stream.of(
        Arguments.of(Locale.forLanguageTag("en"), List.of(), "en"),
        Arguments.of(Locale.forLanguageTag("uk"), List.of(), "uk"),
        Arguments.of(null, List.of(Locale.forLanguageTag("ak"), Locale.forLanguageTag("sq"), Locale.forLanguageTag("am")), "uk"),
        Arguments.of(null, List.of(Locale.forLanguageTag("ak"), Locale.forLanguageTag("uk"), Locale.forLanguageTag("am")), "uk"),
        Arguments.of(null, List.of(Locale.forLanguageTag("ak"), Locale.forLanguageTag("en"), Locale.forLanguageTag("uk")), "en")
    );
  }
}