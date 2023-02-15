package com.task.bookstorewebbapp.repository.captcha;

import com.task.bookstorewebbapp.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CaptchaRepositorySessionImpl implements CaptchaRepository{
  private static final Map<String, String> captchaMap = new HashMap<>();
  private static int id = 0;
  @Override
  public void storeCaptcha(HttpServletRequest request, HttpServletResponse response, String captcha) {
    request.getSession().setAttribute(Constants.CAPTCHA_ID_ATTRIBUTE, id);
    captchaMap.put(String.valueOf(id++),captcha);
  }

  @Override
  public String getCaptchaCode(HttpServletRequest request) {
    String captchaId = String.valueOf(request.getSession().getAttribute(Constants.CAPTCHA_ID_ATTRIBUTE));
    request.getSession().removeAttribute(Constants.CAPTCHA_ID_ATTRIBUTE);
    return captchaMap.getOrDefault(captchaId, null);
  }
}
