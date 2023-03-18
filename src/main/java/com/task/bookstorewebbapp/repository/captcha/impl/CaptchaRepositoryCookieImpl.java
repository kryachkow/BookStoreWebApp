package com.task.bookstorewebbapp.repository.captcha.impl;

import com.task.bookstorewebbapp.repository.captcha.CaptchaRepository;
import com.task.bookstorewebbapp.utils.Constants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CaptchaRepositoryCookieImpl implements CaptchaRepository {

  private static final Map<String, String> captchaMap = new HashMap<>();
  private static int id = 0;

  @Override
  public void storeCaptcha(HttpServletRequest request, HttpServletResponse response,
      String captcha) {
    response.addCookie(new Cookie(Constants.CAPTCHA_ID_ATTRIBUTE, String.valueOf(id)));
    captchaMap.put(String.valueOf(id++), captcha);
  }

  @Override
  public String getCaptchaCode(HttpServletRequest request) {
    String captchaId = "";
    for (Cookie cookie : request.getCookies()) {
      if (cookie.getName().equals(Constants.CAPTCHA_ID_ATTRIBUTE)) {
        captchaId = cookie.getValue();
      }
    }
    String captchaCode = captchaMap.getOrDefault(captchaId, null);
    captchaMap.remove(captchaId);
    return captchaCode;
  }
}
