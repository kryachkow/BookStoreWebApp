package com.task.bookstorewebbapp.utils;

import com.task.bookstorewebbapp.Constants;
import com.task.bookstorewebbapp.model.RegistrationForm;
import jakarta.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class ValidationUtils {

  private static final String EMAIL_PARAMETER = "email";
  private static final String NAME_PARAMETER = "name";
  private static final String SURNAME_PARAMETER = "surname";
  private static final String NICKNAME_PARAMETER = "nickname";
  private static final String PASSWORD_PARAMETER = "password";
  private static final String REPEAT_PASSWORD_PARAMETER = "repeatPassword";
  private static final String MAILING_SUBSCRIPTION_PARAMETER = "mailingSubscription";

  private static final String NAME_REGEX = "^[A-Z][a-z]{3,30}$";
  private static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
  private static final String NICKNAME_REGEX = "^\\D[^@#!]{3,23}";

  private static final Map<Predicate<RegistrationForm>, BiConsumer<RegistrationForm, StringBuilder>> validationMap = new LinkedHashMap<>();

  static {
    validationMap.put((registrationForm -> validateEmail(registrationForm.getEmail())),
        (registrationForm, builder) -> {
          registrationForm.setEmail("");
          builder.append(Constants.EMAIL_NOT_VALID).append(" ");
        });
    validationMap.put((registrationForm -> validateName(registrationForm.getName())),
        (registrationForm, builder) -> {
          registrationForm.setName("");
          builder.append(Constants.NAME_NOT_VALID).append(" ");
        });
    validationMap.put((registrationForm -> validateName(registrationForm.getSurname())),
        (registrationForm, builder) -> {
          registrationForm.setSurname("");
          builder.append(Constants.SURNAME_NOT_VALID).append(" ");
        });
    validationMap.put((registrationForm -> validateNickName(registrationForm.getNickname())),
        (registrationForm, builder) -> {
          registrationForm.setNickname("");
          builder.append(Constants.NICK_NAME_NOT_VALID).append(" ");
        });
    validationMap.put((registrationForm -> validatePassword(registrationForm.getPassword(),
            registrationForm.getRepeatPassword())),
        (registrationForm, builder) -> builder.append(Constants.PASSWORD_NOT_VALID).append(" "));

  }

  private ValidationUtils() {
  }

  public static RegistrationForm getRegForm(HttpServletRequest request) {
    return new RegistrationForm(
        request.getParameter(EMAIL_PARAMETER),
        request.getParameter(NAME_PARAMETER),
        request.getParameter(SURNAME_PARAMETER),
        request.getParameter(NICKNAME_PARAMETER),
        request.getParameter(PASSWORD_PARAMETER),
        request.getParameter(REPEAT_PASSWORD_PARAMETER),
        Boolean.parseBoolean(
            request.getParameter(MAILING_SUBSCRIPTION_PARAMETER)));
  }


  public static String validateRegForm(RegistrationForm registrationForm) {
    StringBuilder errorBuilder = new StringBuilder("");

    validationMap.forEach((predicate, consumer) -> {
      if (!predicate.test(registrationForm)) {
        consumer.accept(registrationForm, errorBuilder);
      }
    });

    return getErrorString(registrationForm, errorBuilder);

  }


  private static boolean validateName(String name) {
    return name != null && name.trim().matches(NAME_REGEX);
  }

  private static boolean validateEmail(String email) {
    return email != null && email.toLowerCase().trim().matches(EMAIL_REGEX);
  }

  private static boolean validatePassword(String password, String repeatPassword) {
    return (password != null && repeatPassword != null) && password.equals(repeatPassword)
        && password.length() >= 8 && password.length() <= 32;
  }

  private static boolean validateNickName(String nickName) {
    return nickName != null && nickName.trim().matches(NICKNAME_REGEX);
  }

  public static String getErrorString(RegistrationForm registrationForm,
      StringBuilder errorBuilder) {
    String error = errorBuilder.toString().trim();

    if (!error.isEmpty()) {
      registrationForm.setPassword("");
      registrationForm.setRepeatPassword("");
    }

    return error;
  }


}
