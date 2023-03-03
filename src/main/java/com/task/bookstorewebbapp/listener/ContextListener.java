package com.task.bookstorewebbapp.listener;

import com.task.bookstorewebbapp.db.DBUtils;
import com.task.bookstorewebbapp.repository.captcha.CaptchaRepository;
import com.task.bookstorewebbapp.repository.captcha.impl.CaptchaRepositoryCookieImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.SQLException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@WebListener
public class ContextListener implements ServletContextListener {

  private static final String LOG4J_LOCATION = "log4j-config-location";
  private static final Logger LOGGER = LogManager.getLogger(ContextListener.class.getName());

  private static CaptchaRepository captchaRepository;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ServletContext context = sce.getServletContext();
    String log4jConfigFile = context.getInitParameter(LOG4J_LOCATION);
    String fullPath = context.getRealPath("") + log4jConfigFile;
    System.out.println(fullPath);
    PropertyConfigurator.configure(fullPath);
    captchaRepository = new CaptchaRepositoryCookieImpl();
    try {
      DBUtils.getInstance().getConnection().close();
    } catch (SQLException e) {

      throw new RuntimeException(e.getMessage(), e);
    }
    LOGGER.info("Application Start");
  }

  public static CaptchaRepository getCaptchaRepository() {
    return captchaRepository;
  }
}
