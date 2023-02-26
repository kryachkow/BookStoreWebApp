package com.task.bookstorewebbapp.service.validation;

import com.task.bookstorewebbapp.Constants;
import com.task.bookstorewebbapp.db.entity.UserEntity;
import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.model.ValidationForm;
import com.task.bookstorewebbapp.repository.avatar.AvatarRepository;
import com.task.bookstorewebbapp.repository.avatar.AvatarRepositoryImpl;
import com.task.bookstorewebbapp.service.user.UserService;
import com.task.bookstorewebbapp.service.user.UserServiceImpl;
import com.task.bookstorewebbapp.utils.ValidationUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.function.BiFunction;

public class AuthenticationService implements ValidationService {

  private static final String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials";

  private final UserService userService = new UserServiceImpl();
  private final AvatarRepository avatarRepository = new AvatarRepositoryImpl();

  private final ArrayList<BiFunction<HttpServletRequest, ValidationForm, String>> validationChain
      = new ArrayList<>();

  {
    validationChain.add(
        (request, registrationForm) -> ValidationUtils.validateSingInForm(registrationForm));
    validationChain.add(
        ((request, validationForm) -> validateCredentials(validationForm, request))
    );
  }

  @Override
  public String validate(HttpServletRequest request, ValidationForm validationForm) {
    String error = "";
    for (BiFunction<HttpServletRequest, ValidationForm, String> function : validationChain) {
      error = function.apply(request, validationForm);
      if (!error.isEmpty()) {
        return error;
      }
    }
    return error;
  }


  private String validateCredentials(ValidationForm validationForm, HttpServletRequest request) {
    UserEntity user = userService.getUserByEmail(validationForm.getEmail());

    if (user != null && user.getPassword().equals(validationForm.getPassword())) {
      User userModel = User.toModel(user);
      userModel.setAvatarSource(avatarRepository.getAvatar(user.getId()));
      request.getSession().setAttribute(Constants.USER_ATTRIBUTE, userModel);
      return "";
    }

    return ValidationUtils.getErrorString(validationForm,
        new StringBuilder(WRONG_CREDENTIALS_MESSAGE));
  }
}
