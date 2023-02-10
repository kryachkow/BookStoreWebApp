package com.task.bookstorewebbapp.servlet;

import com.task.bookstorewebbapp.Constants;
import com.task.bookstorewebbapp.Paths;
import com.task.bookstorewebbapp.entity.UserEntity;
import com.task.bookstorewebbapp.model.RegistrationForm;
import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.service.CaptchaService;
import com.task.bookstorewebbapp.service.UserService;
import com.task.bookstorewebbapp.utils.ValidationUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.BiFunction;

@WebServlet(name = "signUp", value = "/signUp")
public class SingUpServlet extends HttpServlet {

  private static final String USER_ATTRIBUTE = "user";
  private static final String ERROR_ATTRIBUTE = "signUpError";
  private static final String REGISTRATION_FORM_ATTRIBUTE = "registrationForm";
  private final CaptchaService captchaService = new CaptchaService();
  private final ArrayList<BiFunction<HttpServletRequest, RegistrationForm, String>> validationChain
      = new ArrayList<>();

  {
    validationChain.add(
        ((request, registrationForm) -> captchaService.validateCaptcha(request)));
    validationChain.add(
        (request, registrationForm) -> ValidationUtils.validateRegForm(registrationForm));
    validationChain.add(
        (request, registrationForm) -> ValidationUtils.validateCredentialExist(registrationForm));
  }


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
    if (!doChainValidation(request, response, registrationForm)) {
      return;
    }
    ;

    UserEntity userEntity = UserService.addUser(
        registrationForm.getEmail(),
        registrationForm.getName(),
        registrationForm.getSurname(),
        registrationForm.getNickname(),
        registrationForm.getPassword(),
        registrationForm.isMailingSubscription());

    if (userEntity == null) {
      sendError(request, response, registrationForm, Constants.DATABASE_ERROR);
      return;
    }

    request.getSession().setAttribute(USER_ATTRIBUTE, User.toModel(userEntity));
    response.sendRedirect(Paths.INDEX_JSP);
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

  private boolean doChainValidation(HttpServletRequest request, HttpServletResponse response,
      RegistrationForm registrationForm)
      throws IOException {
    String error;
    for (BiFunction<HttpServletRequest, RegistrationForm, String> function : validationChain) {
      error = function.apply(request, registrationForm);
      if (!error.isEmpty()) {
        sendError(request, response, registrationForm, error);
        return false;
      }
    }
    return true;
  }

  private void sendError(HttpServletRequest req, HttpServletResponse resp,
      RegistrationForm regForm, String error)
      throws IOException {
    req.getSession().setAttribute(REGISTRATION_FORM_ATTRIBUTE, regForm);
    req.getSession().setAttribute(ERROR_ATTRIBUTE, error);
    resp.sendRedirect(Paths.SIGN_UP_SERVLET);
  }
}
