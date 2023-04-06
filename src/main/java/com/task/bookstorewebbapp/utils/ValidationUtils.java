package com.task.bookstorewebbapp.utils;

import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.model.UserFormDTO;
import com.task.bookstorewebbapp.model.ValidationDTO;
import jakarta.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ValidationUtils {

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

  private static final Map<Predicate<UserFormDTO>, Consumer<ValidationDTO<User>>> regFormValidationMap = new LinkedHashMap<>();
  private static final Map<Predicate<UserFormDTO>, Consumer<ValidationDTO<User>>> signInValidationMap = new LinkedHashMap<>();

  static {
    regFormValidationMap.put((userFormDTO -> validateEmail(userFormDTO.getEmail())),
        (validationDTO) -> {
          validationDTO.getUserFormDTO().setEmail("");
          validationDTO.getErrorMessage().append(Constants.EMAIL_NOT_VALID).append(" ");
        });
    regFormValidationMap.put((userFormDTO -> validateName(userFormDTO.getName())),
        (validationDTO) -> {
          validationDTO.getUserFormDTO().setName("");
          validationDTO.getErrorMessage().append(Constants.NAME_NOT_VALID).append(" ");
        });
    regFormValidationMap.put((userFormDTO -> validateName(userFormDTO.getSurname())),
        (validationDTO) -> {
          validationDTO.getUserFormDTO().setSurname("");
          validationDTO.getErrorMessage().append(Constants.SURNAME_NOT_VALID).append(" ");
        });
    regFormValidationMap.put((userFormDTO -> validateNickName(userFormDTO.getNickname())),
        (validationDTO) -> {
          validationDTO.getUserFormDTO().setNickname("");
          validationDTO.getErrorMessage().append(Constants.NICK_NAME_NOT_VALID).append(" ");
        });
    regFormValidationMap.put((userFormDTO -> validatePassword(userFormDTO.getPassword(),
            userFormDTO.getRepeatPassword())),
        (validationDTO) -> validationDTO.getErrorMessage().append(Constants.PASSWORD_NOT_VALID).append(" "));

    signInValidationMap.put((userFormDTO -> validateEmail(userFormDTO.getEmail())),
        (validationDTO) -> {
          validationDTO.getUserFormDTO().setEmail("");
          validationDTO.getErrorMessage().append(Constants.EMAIL_NOT_VALID).append(" ");
        });
    signInValidationMap.put((userFormDTO -> validatePassword(userFormDTO.getPassword(),
            userFormDTO.getPassword())),
        (validationDTO) -> validationDTO.getErrorMessage().append(Constants.PASSWORD_NOT_VALID).append(" "));

  }

  private ValidationUtils() {
  }

  public static UserFormDTO getValidationForm(HttpServletRequest request) {
    return new UserFormDTO(
        request.getParameter(EMAIL_PARAMETER),
        request.getParameter(NAME_PARAMETER),
        request.getParameter(SURNAME_PARAMETER),
        request.getParameter(NICKNAME_PARAMETER),
        request.getParameter(PASSWORD_PARAMETER),
        request.getParameter(REPEAT_PASSWORD_PARAMETER),
        Boolean.parseBoolean(
            request.getParameter(MAILING_SUBSCRIPTION_PARAMETER)));
  }


  public static boolean validateRegForm(ValidationDTO<User> validationDTO) {
    return validateForm(validationDTO, regFormValidationMap);
  }

  public static boolean validateSingInForm(ValidationDTO<User> validationDTO) {
     return validateForm(validationDTO, signInValidationMap);
  }


  private static boolean validateForm(ValidationDTO<User> validationDTO,
      Map<Predicate<UserFormDTO>, Consumer<ValidationDTO<User>>> validationMap) {

    validationMap.forEach((predicate, consumer) -> {
      if (!predicate.test(validationDTO.getUserFormDTO())) {
        consumer.accept(validationDTO);
      }
    });

    return checkErrorString(validationDTO);
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

  public static <V> boolean checkErrorString(ValidationDTO<V> validationDTO) {
    if (validationDTO.isErrorExists()) {
      validationDTO.getUserFormDTO().setPassword("");
      validationDTO.getUserFormDTO().setRepeatPassword("");
    }
    return validationDTO.isErrorExists();
  }


}
