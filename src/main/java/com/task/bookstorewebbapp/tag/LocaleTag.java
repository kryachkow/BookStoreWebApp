package com.task.bookstorewebbapp.tag;

import com.task.bookstorewebbapp.repository.locale.LocaleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;

public class LocaleTag extends TagSupport {

  private static LocaleRepository localeRepository;
  private static final String LANG_FORM = """
      <form class="rounded float--left" method="get" >
          <label for="lang">Lang :</label>
          <select id="lang" name="lang" onchange="this.form.submit()">
              %s
          </select>
      </form>""";
  private static final String OPTION = """
              <option value="%s" %s>
                  %s
              </option>
      """;

  public static void setLocaleRepository(
      LocaleRepository localeRepository) {
    LocaleTag.localeRepository = localeRepository;
  }


  @Override
  public int doStartTag() throws JspException {
    JspWriter out = pageContext.getOut();
    Locale locale = localeRepository.getLocaleFormRequest((HttpServletRequest) pageContext.getRequest()).orElse(localeRepository.getBaseLocale());
    Set<Locale> accessibleLocales = localeRepository.getAccessibleLocales();
    StringBuilder optionsBuilder = new StringBuilder();
    accessibleLocales.forEach((loc) -> {
      String selected = loc.getLanguage().equals(locale.getLanguage()) ? "selected" : "";
      optionsBuilder.append(String.format(OPTION, loc.getLanguage(), selected, loc.getDisplayLanguage()));
    });
    try {
      out.print(String.format(LANG_FORM, optionsBuilder));
    } catch (IOException e) {
      throw new JspException(e);
    }
    return SKIP_BODY;
  }
}
