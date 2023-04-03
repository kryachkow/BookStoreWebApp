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

  private static final String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials";

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
        .authenticateUser(validationDTO.getUserFormDTO().getEmail(),
            validationDTO.getUserFormDTO().getPassword())
        .ifPresentOrElse(
            (user -> {
              validationDTO.setReturnValue(user);
              user.setAvatarSource(avatarRepository.getAvatar(user.getId()));
            }),
            () -> validationDTO.getErrorMessage().append(WRONG_CREDENTIALS_MESSAGE)
        );

    return ValidationUtils.getErrorString(validationDTO);
  }


}
