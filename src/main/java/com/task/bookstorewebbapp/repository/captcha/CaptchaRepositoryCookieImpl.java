package com.task.bookstorewebbapp.repository.captcha;

import com.task.bookstorewebbapp.Constants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CaptchaRepositoryCookieImpl implements CaptchaRepository{
  private final Map<String, String> captchaMap = new HashMap<>();
  private int id = 0;

  @Override
  public void storeCaptcha(HttpServletRequest request, HttpServletResponse response, String captcha) {
    response.addCookie(new Cookie(Constants.CAPTCHA_ID_ATTRIBUTE, String.valueOf(id)));
    captchaMap.put(String.valueOf(id++), captcha);
  }

  @Override
  public String getCaptchaCode(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    String captchaId = "";
    for (Cookie cookie: cookies){
      if (cookie.getName().equals(Constants.CAPTCHA_ID_ATTRIBUTE)){
        captchaId = cookie.getValue();
      }
    }
    String captchaCode = captchaMap.getOrDefault(captchaId, "there is no cookie");
    captchaMap.remove(captchaId);
    return captchaCode;
  }
}
