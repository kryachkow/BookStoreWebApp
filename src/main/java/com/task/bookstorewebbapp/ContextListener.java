package com.task.bookstorewebbapp;

import com.task.bookstorewebbapp.repository.captcha.CaptchaRepositoryFieldImpl;
import com.task.bookstorewebbapp.service.CaptchaService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {


  @Override
  public void contextInitialized(ServletContextEvent sce) {
    CaptchaService.setCaptchaRepository(new CaptchaRepositoryFieldImpl());
  }
}
