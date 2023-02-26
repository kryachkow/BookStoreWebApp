package com.task.bookstorewebbapp;

import com.task.bookstorewebbapp.db.DBUtils;
import com.task.bookstorewebbapp.repository.captcha.CaptchaRepository;
import com.task.bookstorewebbapp.repository.captcha.CaptchaRepositoryCookieImpl;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {

  private static CaptchaRepository captchaRepository;


  @Override
  public void contextInitialized(ServletContextEvent sce) {
    captchaRepository = new CaptchaRepositoryCookieImpl();
    try {
      DBUtils.getInstance().getConnection().close();
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static CaptchaRepository getCaptchaRepository() {
    return captchaRepository;
  }
}
