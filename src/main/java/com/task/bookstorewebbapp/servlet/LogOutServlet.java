package com.task.bookstorewebbapp.servlet;

import com.task.bookstorewebbapp.utils.Constants;
import com.task.bookstorewebbapp.utils.ProjectPaths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "logOut", value = "/user/logOut")
public class LogOutServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getSession().invalidate();
    resp.sendRedirect(Constants.FOLDER_EXIT + ProjectPaths.INDEX_JSP);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doGet(req, resp);
  }
}
