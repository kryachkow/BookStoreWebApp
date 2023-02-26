package com.task.bookstorewebbapp.servlet;

import com.task.bookstorewebbapp.Constants;
import com.task.bookstorewebbapp.ProjectPaths;
import com.task.bookstorewebbapp.db.entity.UserEntity;
import com.task.bookstorewebbapp.db.exception.DAOException;
import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.model.ValidationForm;
import com.task.bookstorewebbapp.repository.avatar.AvatarRepository;
import com.task.bookstorewebbapp.repository.avatar.AvatarRepositoryImpl;
import com.task.bookstorewebbapp.service.captcha.CaptchaService;
import com.task.bookstorewebbapp.service.captcha.CaptchaServiceImpl;
import com.task.bookstorewebbapp.service.user.UserService;
import com.task.bookstorewebbapp.service.user.UserServiceImpl;
import com.task.bookstorewebbapp.service.validation.SignUpValidationService;
import com.task.bookstorewebbapp.service.validation.ValidationService;
import com.task.bookstorewebbapp.utils.ServletUtils;
import com.task.bookstorewebbapp.utils.ValidationUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "signUp", value = "/signUp")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 100
)
public class SingUpServlet extends HttpServlet {

  private static final String ERROR_ATTRIBUTE = "signUpError";
  private static final String REGISTRATION_FORM_ATTRIBUTE = "registrationForm";
  private static final String AVATAR_PART = "avatar";

  private final CaptchaService captchaService = new CaptchaServiceImpl();
  private final ValidationService validationService = new SignUpValidationService();
  private final UserService userService = new UserServiceImpl();
  private final AvatarRepository avatarRepository = new AvatarRepositoryImpl();


  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    ServletUtils.sessionAttributesToRequest(request,
        List.of(REGISTRATION_FORM_ATTRIBUTE, ERROR_ATTRIBUTE));
    captchaService.addCaptchaToRequest(request, response);
    request.getRequestDispatcher(ProjectPaths.REGISTER_JSP).forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    ValidationForm registrationForm = ValidationUtils.getValidationForm(request);
    String validationError = validationService.validate(request, registrationForm);
    if (validationError.isEmpty()) {

      UserEntity userEntity;
      try {
        userEntity = getUserFromDataBase(registrationForm);
        User userModel = User.toModel(userEntity);
        avatarRepository.addAvatarToCatalog(request.getPart(AVATAR_PART), userEntity.getId());
        userModel.setAvatarSource(avatarRepository.getAvatar(userModel.getId()));
        request.getSession().setAttribute(Constants.USER_ATTRIBUTE, userModel);
        response.sendRedirect(ProjectPaths.INDEX_JSP);
      } catch (DAOException | ServletException e) {
        sendError(request, response, registrationForm, Constants.DATABASE_ERROR);
      }
      return;
    }
    sendError(request, response, registrationForm, validationError);
  }

  private UserEntity getUserFromDataBase(ValidationForm validationForm) throws DAOException {
    return userService.addUser(
        validationForm.getEmail(),
        validationForm.getName(),
        validationForm.getSurname(),
        validationForm.getNickname(),
        validationForm.getPassword(),
        validationForm.isMailingSubscription());
  }

  private void sendError(HttpServletRequest req, HttpServletResponse resp,
      ValidationForm regForm, String error)
      throws IOException {
    req.getSession().setAttribute(REGISTRATION_FORM_ATTRIBUTE, regForm);
    req.getSession().setAttribute(ERROR_ATTRIBUTE, error);
    resp.sendRedirect(ProjectPaths.SIGN_UP_SERVLET);
  }


}
