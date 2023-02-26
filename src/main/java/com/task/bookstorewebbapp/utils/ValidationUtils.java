package com.task.bookstorewebbapp.utils;

import com.task.bookstorewebbapp.Constants;
import com.task.bookstorewebbapp.model.ValidationForm;
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

  private static final String NAME_REGEX = "^[A-Z][a-z]{3,31}$";

  private static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
  private static final String NICKNAME_REGEX = "^\\D[^@#!]{3,31}";

  private static final Map<Predicate<ValidationForm>, BiConsumer<ValidationForm, StringBuilder>> regFormValidationMap = new LinkedHashMap<>();
  private static final Map<Predicate<ValidationForm>, BiConsumer<ValidationForm, StringBuilder>> signInValidationMap = new LinkedHashMap<>();

  static {
    regFormValidationMap.put((registrationForm -> validateEmail(registrationForm.getEmail())),
        (registrationForm, builder) -> {
          registrationForm.setEmail("");
          builder.append(Constants.EMAIL_NOT_VALID).append(" ");
        });
    regFormValidationMap.put((registrationForm -> validateName(registrationForm.getName())),
        (registrationForm, builder) -> {
          registrationForm.setName("");
          builder.append(Constants.NAME_NOT_VALID).append(" ");
        });
    regFormValidationMap.put((registrationForm -> validateName(registrationForm.getSurname())),
        (registrationForm, builder) -> {
          registrationForm.setSurname("");
          builder.append(Constants.SURNAME_NOT_VALID).append(" ");
        });
    regFormValidationMap.put((registrationForm -> validateNickName(registrationForm.getNickname())),
        (registrationForm, builder) -> {
          registrationForm.setNickname("");
          builder.append(Constants.NICK_NAME_NOT_VALID).append(" ");
        });
    regFormValidationMap.put((registrationForm -> validatePassword(registrationForm.getPassword(),
            registrationForm.getRepeatPassword())),
        (registrationForm, builder) -> builder.append(Constants.PASSWORD_NOT_VALID).append(" "));

    signInValidationMap.put((validationForm -> validateEmail(validationForm.getEmail())),
        (validationForm, builder) -> {
          validationForm.setEmail("");
          builder.append(Constants.EMAIL_NOT_VALID).append(" ");
        });
    signInValidationMap.put((validationForm -> validatePassword(validationForm.getPassword(),
            validationForm.getPassword())),
        (validationForm, builder) -> builder.append(Constants.PASSWORD_NOT_VALID).append(" "));

  }

  private ValidationUtils() {
  }

  public static ValidationForm getValidationForm(HttpServletRequest request) {
    return new ValidationForm(
        request.getParameter(EMAIL_PARAMETER),
        request.getParameter(NAME_PARAMETER),
        request.getParameter(SURNAME_PARAMETER),
        request.getParameter(NICKNAME_PARAMETER),
        request.getParameter(PASSWORD_PARAMETER),
        request.getParameter(REPEAT_PASSWORD_PARAMETER),
        Boolean.parseBoolean(
            request.getParameter(MAILING_SUBSCRIPTION_PARAMETER)));
  }


  public static String validateRegForm(ValidationForm validationForm) {
    return validateForm(validationForm, regFormValidationMap);
  }

  public static String validateSingInForm(ValidationForm validationForm) {
    return validateForm(validationForm, signInValidationMap);
  }


  private static String validateForm(ValidationForm validationForm,
      Map<Predicate<ValidationForm>, BiConsumer<ValidationForm, StringBuilder>> validationMap) {
    StringBuilder errorBuilder = new StringBuilder("");

    validationMap.forEach((predicate, consumer) -> {
      if (!predicate.test(validationForm)) {
        consumer.accept(validationForm, errorBuilder);
      }
    });

    return getErrorString(validationForm, errorBuilder);
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

  public static String getErrorString(ValidationForm validationForm,
      StringBuilder errorBuilder) {
    String error = errorBuilder.toString().trim();

    if (!error.isEmpty()) {
      validationForm.setPassword("");
      validationForm.setRepeatPassword("");
    }

    return error;
  }


}
