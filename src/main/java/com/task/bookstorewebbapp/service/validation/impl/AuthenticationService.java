package com.task.bookstorewebbapp.service.validation.impl;

import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.model.ValidationDTO;
import com.task.bookstorewebbapp.repository.avatar.AvatarRepository;
import com.task.bookstorewebbapp.repository.avatar.impl.AvatarRepositoryImpl;
import com.task.bookstorewebbapp.service.user.UserService;
import com.task.bookstorewebbapp.service.user.impl.UserServiceImpl;
import com.task.bookstorewebbapp.service.validation.ValidationService;
import com.task.bookstorewebbapp.utils.ValidationUtils;
import java.util.ArrayList;
import java.util.function.Consumer;

public class AuthenticationService implements ValidationService<User> {

  private static final String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials. ";
  private static final String BAN_MESSAGE = "Due to many failed attempts of logging in, your account was banned.";

  private final UserService userService = new UserServiceImpl();
  private final AvatarRepository avatarRepository = new AvatarRepositoryImpl();

  private final ArrayList<Consumer<ValidationDTO<User>>> validationChain
      = new ArrayList<>();

  {
    validationChain.add(
        ValidationUtils::validateSingInForm);
    validationChain.add(
        (this::validateCredentials)
    );
    validationChain.add(this::setUpAvatar);


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


  private boolean validateCredentials(ValidationDTO<User> validationDTO) {
    userService
        .getUserByEmail(validationDTO.getUserFormDTO().getEmail())
        .ifPresentOrElse(
            (user -> {
              boolean passwordCheck = userService.passwordCheck(user,
                  validationDTO.getUserFormDTO().getPassword());
              if (!passwordCheck) {
                validationDTO.getErrorMessage().append(WRONG_CREDENTIALS_MESSAGE);
              }
              if (userService.isBanned(user, passwordCheck)) {
                validationDTO.getErrorMessage().append(BAN_MESSAGE);
              }
              if (!validationDTO.isErrorExists()) {
                validationDTO.setReturnValue(user);
              }
            }),
            () -> validationDTO.getErrorMessage().append(WRONG_CREDENTIALS_MESSAGE)
        );

    return ValidationUtils.checkErrorString(validationDTO);
  }

  private boolean setUpAvatar(ValidationDTO<User> validationDTO) {
    validationDTO.getReturnValue()
        .setAvatarSource(avatarRepository.getAvatar(validationDTO.getReturnValue().getId()));
    return ValidationUtils.checkErrorString(validationDTO);
  }


}
