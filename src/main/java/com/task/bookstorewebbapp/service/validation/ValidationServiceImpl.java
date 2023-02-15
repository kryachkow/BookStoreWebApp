package com.task.bookstorewebbapp.service.validation;

import com.task.bookstorewebbapp.Constants;
import com.task.bookstorewebbapp.model.RegistrationForm;
import com.task.bookstorewebbapp.service.captcha.CaptchaService;
import com.task.bookstorewebbapp.service.captcha.CaptchaServiceImpl;
import com.task.bookstorewebbapp.service.user.UserService;
import com.task.bookstorewebbapp.service.user.UserServiceImpl;
import com.task.bookstorewebbapp.utils.ValidationUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ValidationServiceImpl implements ValidationService {

  private final CaptchaService captchaService = new CaptchaServiceImpl();
  private final UserService userService = new UserServiceImpl();

  private final ArrayList<BiFunction<HttpServletRequest, RegistrationForm, String>> validationChain
      = new ArrayList<>();

  private final Map<Predicate<RegistrationForm>, BiConsumer<RegistrationForm, StringBuilder>> credentialsValidationMap = new LinkedHashMap<>();

  {
    validationChain.add(
        ((request, registrationForm) -> captchaService.validateCaptcha(request)));
    validationChain.add(
        (request, registrationForm) -> ValidationUtils.validateRegForm(registrationForm));
    validationChain.add(
        (request, registrationForm) -> validateCredentialExist(registrationForm));

    credentialsValidationMap.put(
        (registrationForm -> userService.getUserByEmail(registrationForm.getEmail()) == null),
        (registrationForm, builder) -> {
          registrationForm.setEmail("");
          builder.append(Constants.EMAIL_EXISTS).append(" ");
        });
    credentialsValidationMap.put(
        (registrationForm -> userService.getUserByNickname(registrationForm.getNickname()) == null),
        (registrationForm, builder) -> {
          registrationForm.setNickname("");
          builder.append(Constants.NICKNAME_EXISTS).append(" ");
        });
  }

  @Override
  public String validate(HttpServletRequest request,
      RegistrationForm registrationForm) {
    String error = "";
    for (BiFunction<HttpServletRequest, RegistrationForm, String> function : validationChain) {
      error = function.apply(request, registrationForm);
      if (!error.isEmpty()) {
        return error;
      }
    }
    return error;
  }

  public String validateCredentialExist(RegistrationForm registrationForm) {
    StringBuilder errorBuilder = new StringBuilder();

    credentialsValidationMap.forEach((predicate, consumer) -> {
      if (!predicate.test(registrationForm)) {
        consumer.accept(registrationForm, errorBuilder);
      }
    });

    return ValidationUtils.getErrorString(registrationForm, errorBuilder);
  }
}

