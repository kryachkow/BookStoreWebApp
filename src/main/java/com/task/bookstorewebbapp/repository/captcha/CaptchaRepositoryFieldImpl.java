package com.task.bookstorewebbapp.repository.captcha;

import com.task.bookstorewebbapp.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CaptchaRepositoryFieldImpl implements CaptchaRepository {

  private static final Map<String, String> captchaMap = new HashMap<>();
  private static int id = 0;

  @Override
  public void storeCaptcha(HttpServletRequest request, HttpServletResponse response,
      String captcha) {
    request.setAttribute(Constants.CAPTCHA_ID_ATTRIBUTE, id);
    captchaMap.put(String.valueOf(id++), captcha);
  }

  @Override
  public String getCaptchaCode(HttpServletRequest request) {
    String captchaId = String.valueOf(request.getParameter(Constants.CAPTCHA_ID_ATTRIBUTE));
    String captchaCode = captchaMap.getOrDefault(captchaId, null);
    captchaMap.remove(captchaId);
    return captchaCode;
  }
}
