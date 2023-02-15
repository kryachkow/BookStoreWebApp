package com.task.bookstorewebbapp.servlet;

import com.task.bookstorewebbapp.Constants;
import com.task.bookstorewebbapp.Paths;
import com.task.bookstorewebbapp.entity.UserEntity;
import com.task.bookstorewebbapp.model.RegistrationForm;
import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.service.captcha.CaptchaService;
import com.task.bookstorewebbapp.service.captcha.CaptchaServiceImpl;
import com.task.bookstorewebbapp.service.user.UserService;
import com.task.bookstorewebbapp.service.user.UserServiceImpl;
import com.task.bookstorewebbapp.service.validation.ValidationService;
import com.task.bookstorewebbapp.service.validation.ValidationServiceImpl;
import com.task.bookstorewebbapp.utils.ValidationUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "signUp", value = "/signUp")
public class SingUpServlet extends HttpServlet {

  private static final String USER_ATTRIBUTE = "user";
  private static final String ERROR_ATTRIBUTE = "signUpError";
  private static final String REGISTRATION_FORM_ATTRIBUTE = "registrationForm";
  private final CaptchaService captchaService = new CaptchaServiceImpl();
  private final ValidationService validationService = new ValidationServiceImpl();
  private final UserService userService = new UserServiceImpl();


  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    cleanUpSessionAttributes(request);
    captchaService.addCaptchaToRequest(request, response);
    request.getRequestDispatcher(Paths.REGISTER_JSP).forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    RegistrationForm registrationForm = ValidationUtils.getRegForm(request);
    String validationError = validationService.validate(request, registrationForm);
    if (validationError.isEmpty()) {
      UserEntity userEntity = getUserFromDataBase(registrationForm);

      if (userEntity != null) {
        request.getSession().setAttribute(USER_ATTRIBUTE, User.toModel(userEntity));
        response.sendRedirect(Paths.INDEX_JSP);
      } else {
        sendError(request, response, registrationForm, Constants.DATABASE_ERROR);
      }
      return;
    }
    sendError(request, response, registrationForm, validationError);
  }

  private UserEntity getUserFromDataBase(RegistrationForm registrationForm) {
    return userService.addUser(
        registrationForm.getEmail(),
        registrationForm.getName(),
        registrationForm.getSurname(),
        registrationForm.getNickname(),
        registrationForm.getPassword(),
        registrationForm.isMailingSubscription());
  }

  private void cleanUpSessionAttributes(HttpServletRequest request) {
    RegistrationForm registrationForm = (RegistrationForm) request.getSession()
        .getAttribute(REGISTRATION_FORM_ATTRIBUTE);
    String error = (String) request.getSession().getAttribute(ERROR_ATTRIBUTE);
    request.getSession().removeAttribute(REGISTRATION_FORM_ATTRIBUTE);
    request.getSession().removeAttribute(ERROR_ATTRIBUTE);
    request.setAttribute(REGISTRATION_FORM_ATTRIBUTE, registrationForm);
    request.setAttribute(ERROR_ATTRIBUTE, error);
  }

  private void sendError(HttpServletRequest req, HttpServletResponse resp,
      RegistrationForm regForm, String error)
      throws IOException {
    req.getSession().setAttribute(REGISTRATION_FORM_ATTRIBUTE, regForm);
    req.getSession().setAttribute(ERROR_ATTRIBUTE, error);
    resp.sendRedirect(Paths.SIGN_UP_SERVLET);
  }


}
