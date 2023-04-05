package com.task.bookstorewebbapp.service.validation.impl;

import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.model.ValidationDTO;
import com.task.bookstorewebbapp.service.captcha.CaptchaService;
import com.task.bookstorewebbapp.service.captcha.impl.CaptchaServiceImpl;
import com.task.bookstorewebbapp.service.user.UserService;
import com.task.bookstorewebbapp.service.user.impl.UserServiceImpl;
import com.task.bookstorewebbapp.service.validation.ValidationService;
import com.task.bookstorewebbapp.utils.Constants;
import com.task.bookstorewebbapp.utils.ValidationUtils;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SignUpValidationService implements ValidationService<User> {

  private final CaptchaService captchaService = new CaptchaServiceImpl();
  private final UserService userService = new UserServiceImpl();

  private final ArrayList<Consumer<ValidationDTO<User>>> validationChain = new ArrayList<>();

  private final Map<Predicate<ValidationDTO<User>>, Consumer<ValidationDTO<User>>> credentialsValidationMap
      = new LinkedHashMap<>();

  {
    validationChain.add(
        (validationDTO -> captchaService.validateCaptcha(validationDTO.getRequest())));
    validationChain.add((ValidationUtils::validateRegForm));
    validationChain.add(this::validateCredentialExist);

    credentialsValidationMap.put(
        (validationDTO -> userService.getUserByEmail((validationDTO.getUserFormDTO().getEmail()))
            .isEmpty()), (validationDTO -> {
          validationDTO.getUserFormDTO().setEmail("");
          validationDTO.getErrorMessage().append(Constants.EMAIL_EXISTS).append(" ");
        }));
    credentialsValidationMap.put((validationDTO ->
            userService.getUserByNickname((validationDTO.getUserFormDTO().getNickname())).isEmpty()),
        (validationDTO -> {
          validationDTO.getUserFormDTO().setNickname("");
          validationDTO.getErrorMessage().append(Constants.NICKNAME_EXISTS).append(" ");
        }));
  }

  @Override
  public boolean checkErrors(ValidationDTO<User> validationDTO) {
    for (Consumer<ValidationDTO<User>> consumer : validationChain) {
      consumer.accept(validationDTO);
      if (validationDTO.isErrorExists()) {
        return validationDTO.isErrorExists();
      }
    }
    return false;
  }

  public boolean validateCredentialExist(ValidationDTO<User> validationDTO) {

    credentialsValidationMap.forEach((predicate, consumer) -> {
      if (!predicate.test(validationDTO)) {
        consumer.accept(validationDTO);
      }
    });

    return ValidationUtils.checkErrorString(validationDTO);
  }
}

