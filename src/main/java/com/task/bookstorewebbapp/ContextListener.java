package com.task.bookstorewebbapp;

import com.task.bookstorewebbapp.repository.captcha.CaptchaRepository;
import com.task.bookstorewebbapp.repository.captcha.CaptchaRepositoryCookieImpl;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

  private static CaptchaRepository captchaRepository;


  @Override
  public void contextInitialized(ServletContextEvent sce) {
    captchaRepository = new CaptchaRepositoryCookieImpl();
  }

  public static CaptchaRepository getCaptchaRepository() {
    return captchaRepository;
  }
}
