package com.task.bookstorewebbapp.servlet;

import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.model.UserFormDTO;
import com.task.bookstorewebbapp.model.ValidationDTO;
import com.task.bookstorewebbapp.repository.avatar.AvatarRepository;
import com.task.bookstorewebbapp.repository.avatar.impl.AvatarRepositoryImpl;
import com.task.bookstorewebbapp.service.captcha.CaptchaService;
import com.task.bookstorewebbapp.service.captcha.impl.CaptchaServiceImpl;
import com.task.bookstorewebbapp.service.user.UserService;
import com.task.bookstorewebbapp.service.user.impl.UserServiceImpl;
import com.task.bookstorewebbapp.service.validation.ValidationService;
import com.task.bookstorewebbapp.service.validation.impl.SignUpValidationService;
import com.task.bookstorewebbapp.utils.Constants;
import com.task.bookstorewebbapp.utils.ProjectPaths;
import com.task.bookstorewebbapp.utils.ServletUtils;
import com.task.bookstorewebbapp.utils.ValidationUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@WebServlet(name = "signUp", value = "/signUp")
@MultipartConfig(
    fileSizeThreshold = Constants.FILE_SIZE_THRESHOLD,
    maxFileSize = Constants.MAX_FILE_SIZE,
    maxRequestSize = Constants.MAX_REQUEST_SIZE
)
public class SingUpServlet extends HttpServlet {

  private static final String ERROR_ATTRIBUTE = "signUpError";
  private static final String REGISTRATION_FORM_ATTRIBUTE = "registrationForm";
  private static final String AVATAR_PART = "avatar";
  private static final Logger LOGGER = LogManager.getLogger(SingUpServlet.class.getName());


  private final CaptchaService captchaService = new CaptchaServiceImpl();
  private final ValidationService<User> validationService = new SignUpValidationService();
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
    ValidationDTO<User> validationDTO = new ValidationDTO<>(request, ValidationUtils.getValidationForm(request));

    if (!validationService.checkErrors(validationDTO)) {
      try {
        User userModel = getUserFromDataBase(validationDTO.getUserFormDTO());
        avatarRepository.addAvatarToCatalog(request.getPart(AVATAR_PART), userModel.getId());
        userModel.setAvatarSource(avatarRepository.getAvatar(userModel.getId()));
        request.getSession().setAttribute(Constants.USER_ATTRIBUTE, userModel);
        response.sendRedirect(ProjectPaths.INDEX_JSP);
      } catch (SQLException | ServletException e) {
        LOGGER.error(Constants.DATABASE_ERROR, e);
        sendError(request, response, validationDTO.getUserFormDTO(), Constants.DATABASE_ERROR);
      }
      return;
    }
    sendError(request, response, validationDTO.getUserFormDTO(), validationDTO.getErrorMessage().toString().trim());
  }

  private User getUserFromDataBase(UserFormDTO userFormDTO) throws SQLException {
    return userService.addUser(
        userFormDTO.getEmail(),
        userFormDTO.getName(),
        userFormDTO.getSurname(),
        userFormDTO.getNickname(),
        userFormDTO.getPassword(),
        userFormDTO.isMailingSubscription()).orElseThrow(SQLException::new);
  }

  private void sendError(HttpServletRequest req, HttpServletResponse resp,
      UserFormDTO regForm, String error)
      throws IOException {
    req.getSession().setAttribute(REGISTRATION_FORM_ATTRIBUTE, regForm);
    req.getSession().setAttribute(ERROR_ATTRIBUTE, error);
    resp.sendRedirect(ProjectPaths.SIGN_UP_SERVLET);
  }


}
