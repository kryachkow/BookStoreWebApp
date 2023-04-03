package com.task.bookstorewebbapp.listener;

import com.task.bookstorewebbapp.db.DBUtils;
import com.task.bookstorewebbapp.filter.LocaleFilter;
import com.task.bookstorewebbapp.repository.captcha.CaptchaRepository;
import com.task.bookstorewebbapp.repository.captcha.impl.CaptchaRepositoryCookieImpl;
import com.task.bookstorewebbapp.repository.locale.impl.CookieLocaleRepositoryImpl;
import com.task.bookstorewebbapp.tag.LocaleTag;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@WebListener
public class ContextListener implements ServletContextListener {

  private static final String LOG4J_LOCATION = "log4j-config-location";
  private static final String BASE_LOCALE_LOCATION = "javax.servlet.jsp.jstl.fmt.locale";
  private static final String LOCALES = "locales";


  private static final int COOKIE_AGE = 10800;
  private static final Logger LOGGER = LogManager.getLogger(ContextListener.class.getName());

  private static CaptchaRepository captchaRepository;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ServletContext context = sce.getServletContext();
    String log4jConfigFile = context.getInitParameter(LOG4J_LOCATION);
    String fullPath = context.getRealPath("") + log4jConfigFile;
    PropertyConfigurator.configure(fullPath);
    captchaRepository = new CaptchaRepositoryCookieImpl();
    String localesFileName = context.getInitParameter(LOCALES);
    String localesFileRealPath = context.getRealPath(localesFileName);
    Properties locales = new Properties();

    try {
      DBUtils.getInstance().getConnection().close();
      locales.load(Files.newInputStream(Paths.get(localesFileRealPath)));
    } catch (SQLException | IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    context.setAttribute(LOCALES, locales);
    CookieLocaleRepositoryImpl cookieLocaleRepository = new CookieLocaleRepositoryImpl(locales,
        Locale.forLanguageTag(context.getInitParameter(BASE_LOCALE_LOCATION)), COOKIE_AGE);
    LocaleFilter.setLocaleRepository(cookieLocaleRepository);
    LocaleTag.setLocaleRepository(cookieLocaleRepository);
    LOGGER.info("Application Start");
  }

  public static CaptchaRepository getCaptchaRepository() {
    return captchaRepository;
  }

}
