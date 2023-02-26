package com.task.bookstorewebbapp.servlet;


import com.task.bookstorewebbapp.ProjectPaths;
import com.task.bookstorewebbapp.model.ValidationForm;
import com.task.bookstorewebbapp.service.validation.AuthenticationService;
import com.task.bookstorewebbapp.service.validation.ValidationService;
import com.task.bookstorewebbapp.utils.ServletUtils;
import com.task.bookstorewebbapp.utils.ValidationUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "signIn", value = "/signIn")
public class SignInServlet extends HttpServlet {

  private final ValidationService validationService = new AuthenticationService();
  private static final String ERROR_ATTRIBUTE = "signInError";
  private static final String SIGN_IN_FORM_ATTRIBUTE = "signInForm";


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    ServletUtils.sessionAttributesToRequest(req, List.of(SIGN_IN_FORM_ATTRIBUTE, ERROR_ATTRIBUTE));
    req.getRequestDispatcher(ProjectPaths.SING_IN_JSP).forward(req, resp);

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    ValidationForm validationForm = ValidationUtils.getValidationForm(req);
    String validationError = validationService.validate(req, validationForm);

    if (!validationError.isEmpty()) {
      req.getSession().setAttribute(SIGN_IN_FORM_ATTRIBUTE, validationForm);
      req.getSession().setAttribute(ERROR_ATTRIBUTE, validationError);
      resp.sendRedirect(ProjectPaths.SIGN_IN_SERVLET);

    } else {

      resp.sendRedirect(ProjectPaths.INDEX_JSP);
    }
  }
}
