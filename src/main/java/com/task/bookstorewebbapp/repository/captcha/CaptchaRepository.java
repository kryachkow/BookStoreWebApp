package com.task.bookstorewebbapp.repository.captcha;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CaptchaRepository {


  void storeCaptcha(HttpServletRequest request, HttpServletResponse response, String captcha);

  String getCaptchaCode(HttpServletRequest request);

}
