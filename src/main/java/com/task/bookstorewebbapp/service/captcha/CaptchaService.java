package com.task.bookstorewebbapp.service.captcha;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface CaptchaService {

  void addCaptchaToRequest(HttpServletRequest request, HttpServletResponse response)
      throws IOException;

  String validateCaptcha(HttpServletRequest request);
}