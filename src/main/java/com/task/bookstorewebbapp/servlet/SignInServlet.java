package com.task.bookstorewebbapp.servlet;


import com.task.bookstorewebbapp.model.User;
import com.task.bookstorewebbapp.model.ValidationDTO;
import com.task.bookstorewebbapp.service.validation.ValidationService;
import com.task.bookstorewebbapp.service.validation.impl.AuthenticationService;
import com.task.bookstorewebbapp.utils.Constants;
import com.task.bookstorewebbapp.utils.ProjectPaths;
import com.task.bookstorewebbapp.utils.ServletUtils;
import com.task.bookstorewebbapp.utils.ValidationUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "signIn", value = "/guest/signIn")
public class SignInServlet extends HttpServlet {

  private final ValidationService<User> validationService = new AuthenticationService();
  private static final String ERROR_ATTRIBUTE = "signInError";
  private static final String SIGN_IN_FORM_ATTRIBUTE = "signInForm";
  private static final String GO_BACK_TO_PARAM = "goBackTo";


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    ServletUtils.sessionAttributesToRequest(req, List.of(SIGN_IN_FORM_ATTRIBUTE, ERROR_ATTRIBUTE, GO_BACK_TO_PARAM));
    req.getRequestDispatcher(ProjectPaths.SING_IN_JSP).forward(req, resp);

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    ValidationDTO<User> validationDTO = new ValidationDTO<>(req, ValidationUtils.getValidationForm(req));
    Optional<String> goBackToParam = Optional.ofNullable(req.getParameter(GO_BACK_TO_PARAM));

    if (validationService.checkErrors(validationDTO)) {
      req.getSession().setAttribute(SIGN_IN_FORM_ATTRIBUTE, validationDTO.getUserFormDTO());
      req.getSession().setAttribute(ERROR_ATTRIBUTE, validationDTO.getErrorMessage().toString().trim());
      goBackToParam.ifPresent(param -> req.getSession().setAttribute(GO_BACK_TO_PARAM, param));
      resp.sendRedirect(Constants.FOLDER_EXIT + ProjectPaths.SIGN_IN_SERVLET);
    } else {
      StringBuilder returnURl = new StringBuilder();
      req.getSession().setAttribute(Constants.USER_ATTRIBUTE, validationDTO.getReturnValue());
      goBackToParam.ifPresentOrElse(returnURl::append, () -> returnURl.append(Constants.FOLDER_EXIT + ProjectPaths.INDEX_JSP));
      resp.sendRedirect(returnURl.toString());
    }
  }
}
