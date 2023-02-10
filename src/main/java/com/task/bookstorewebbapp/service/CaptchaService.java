package com.task.bookstorewebbapp.service;

import com.task.bookstorewebbapp.Constants;
import com.task.bookstorewebbapp.repository.captcha.CaptchaRepository;
import com.task.bookstorewebbapp.repository.captcha.CaptchaRepositoryCookieImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;
import javax.imageio.ImageIO;

public class CaptchaService {
  private static final String CAPTCHA_INPUT_PARAMETER = "captchaInput";
  private static final String TIMER_ATTRIBUTE = "signUpTimer";
  private static final String TIME_EXCEEDED_MESSAGE = "Time exceeded";
  private static final String CAPTCHA_INVALID_MESSAGE = "Captcha is invalid, try again";
  private static final long seed = 1032183012973129L;
  private static final Random random = new Random(seed);
  private static int targetStringLength = 6;

  private static CaptchaRepository captchaRepository = new CaptchaRepositoryCookieImpl();

  public void addCaptchaToRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String captchaCode = generateCaptchaCode();
    captchaRepository.storeCaptcha(request, response, captchaCode);
    request.setAttribute(Constants.CAPTCHA_IMAGE_ATTRIBUTE, getBase64Image(captchaCode));
    request.getSession().setAttribute("signUpTimer", System.currentTimeMillis());
  }

  public String validateCaptcha(HttpServletRequest request) {
    if (!captchaRepository.getCaptchaCode(request).equals(request.getParameter(
        CAPTCHA_INPUT_PARAMETER))) {
      return CAPTCHA_INVALID_MESSAGE;
    }
    if(System.currentTimeMillis() - (long) request.getSession().getAttribute("signUpTimer") > 60000 ) {
      return TIME_EXCEEDED_MESSAGE;
    }

    return CAPTCHA_INVALID_MESSAGE;
  }

  public static void setCaptchaRepository(CaptchaRepository captchaRepository) {
    CaptchaService.captchaRepository = captchaRepository;
  }


  public  String generateCaptchaCode(){
    return  random.ints(48, 58)
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }

  public  String getBase64Image(String code) throws IOException {

    BufferedImage image = new BufferedImage(120, 30, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics2D = image.createGraphics();
    Color c = new Color(0.6662f, 0.4569f, 0.3232f);
    GradientPaint gp = new GradientPaint(30, 30, c, 15, 25, Color.white, true);
    graphics2D.setPaint(gp);
    Font font=new Font("Verdana", Font.BOLD, 26);
    graphics2D.setFont(font);
    graphics2D.drawString(code,2,20);
    graphics2D.dispose();

    final ByteArrayOutputStream os = new ByteArrayOutputStream();
    ImageIO.write(image, "jpeg", os);

    return Base64.getEncoder().encodeToString(os.toByteArray());
  }
}
