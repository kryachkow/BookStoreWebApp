package com.task.bookstorewebbapp.service.captcha;

import com.task.bookstorewebbapp.Constants;
import com.task.bookstorewebbapp.ContextListener;
import com.task.bookstorewebbapp.repository.captcha.CaptchaRepository;
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.imageio.ImageIO;

public class CaptchaServiceImpl implements CaptchaService {

  private static final String CAPTCHA_INPUT_PARAMETER = "captchaInput";
  private static final String TIMER_ATTRIBUTE = "signUpTimer";
  private static final String TIME_EXCEEDED_MESSAGE = "Time exceeded";
  private static final String CAPTCHA_INVALID_MESSAGE = "Captcha is invalid, try again";
  private static final long seed = 1032183012973129L;
  private static final Random random = new Random(seed);
  private final static int targetStringLength = 6;
  private static final Font FONT = new Font("Verdana", Font.BOLD, 26);
  private static final Color COLOR = new Color(0.6662f, 0.4569f, 0.3232f);
  private static final GradientPaint GRADIENT_PAINT = new GradientPaint(30, 30, COLOR, 15, 25,
      Color.white, true);
  private static final String IMAGE_FORMAT = "jpeg";
  private final CaptchaRepository captchaRepository = ContextListener.getCaptchaRepository();
  private final Map<Predicate<HttpServletRequest>, Supplier<String>> captchaValidationMap = new LinkedHashMap<>();

  {
    captchaValidationMap.put((request -> {
      String captchaCode = captchaRepository.getCaptchaCode(request);
      if (captchaCode != null) {
        return captchaCode.equals(request.getParameter(
            CAPTCHA_INPUT_PARAMETER));
      }
      return false;
    }), () -> CAPTCHA_INVALID_MESSAGE);
    captchaValidationMap.put((request ->
            System.currentTimeMillis() - (long) request.getSession().getAttribute(TIMER_ATTRIBUTE)
                <= 60000),
        () -> TIME_EXCEEDED_MESSAGE);
  }

  @Override
  public void addCaptchaToRequest(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    String captchaCode = generateCaptchaCode();
    captchaRepository.storeCaptcha(request, response, captchaCode);
    request.setAttribute(Constants.CAPTCHA_IMAGE_ATTRIBUTE, getBase64Image(captchaCode));
    request.getSession().setAttribute(TIMER_ATTRIBUTE, System.currentTimeMillis());
  }

  @Override
  public String validateCaptcha(HttpServletRequest request) {
    StringBuilder builder = new StringBuilder();
    captchaValidationMap.forEach((predicate, supplier) ->
    {
      if (!predicate.test(request)) {
        builder.append(supplier.get());
      }
    });
    return builder.toString().trim();
  }

  private String generateCaptchaCode() {
    return random.ints(48, 58)
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }

  private String getBase64Image(String code) throws IOException {
    BufferedImage image = new BufferedImage(120, 30, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics2D = image.createGraphics();
    graphics2D.setPaint(GRADIENT_PAINT);
    graphics2D.setFont(FONT);
    graphics2D.drawString(code, 2, 20);
    graphics2D.dispose();
    final ByteArrayOutputStream os = new ByteArrayOutputStream();
    ImageIO.write(image, IMAGE_FORMAT, os);

    return Base64.getEncoder().encodeToString(os.toByteArray());
  }


}
