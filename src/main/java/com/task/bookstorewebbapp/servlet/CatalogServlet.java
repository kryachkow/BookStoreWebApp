package com.task.bookstorewebbapp.servlet;

import com.task.bookstorewebbapp.model.CatalogFilterDTO;
import com.task.bookstorewebbapp.model.PaginationDTO;
import com.task.bookstorewebbapp.repository.catalog.impl.CatalogRepositoryImpl;
import com.task.bookstorewebbapp.service.catalog.CatalogService;
import com.task.bookstorewebbapp.service.catalog.impl.CatalogServiceImpl;
import com.task.bookstorewebbapp.utils.Constants;
import com.task.bookstorewebbapp.utils.ProjectPaths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@WebServlet(name = "catalog", value = "/catalog")
public class CatalogServlet extends HttpServlet {

  private static final String BOOKS = "books";
  private static final String CATEGORIES = "categories";
  private static final String PUBLISHERS = "publishers";
  private static final String CATALOG_FILTER = "catalogFilter";
  private static final String PAGINATION = "pagination";
  private static final String ERROR = "Couldn't get books form database, please try again later";
  private static final String ERROR_ATTRIBUTE = "catalog error";

  private static final Logger LOGGER = LogManager.getLogger(CatalogServlet.class.getName());


  private final CatalogService catalogService = new CatalogServiceImpl(new CatalogRepositoryImpl());


  public CatalogServlet() throws SQLException {
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getParameterMap().size() < Constants.MIN_CATALOG_PARAMS) {
      resp.sendRedirect(ProjectPaths.CATALOG_SERVLET + ProjectPaths.BASE_CATALOG_PARAMETERS);
      return;
    }
    CatalogFilterDTO catalogFilterDTO = catalogService.getCatalogFilterDTO(req);
    PaginationDTO paginatingDTO = catalogService.getPaginatingDTO(req);
    try {
      req.setAttribute(BOOKS, catalogService.getBooks(catalogFilterDTO, paginatingDTO));
      req.setAttribute(CATEGORIES, catalogService.getCategories());
      req.setAttribute(PUBLISHERS, catalogService.getPublishers());
      req.setAttribute(CATALOG_FILTER, catalogFilterDTO);
      req.setAttribute(PAGINATION, paginatingDTO);
      req.getRequestDispatcher(ProjectPaths.CATALOG_JSP).forward(req, resp);
    } catch (SQLException e) {
      LOGGER.error("Couldn't get books from catalog", e);
      req.setAttribute(ERROR_ATTRIBUTE, ERROR);
      req.getRequestDispatcher(ProjectPaths.CATALOG_JSP).forward(req, resp);
    }

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.sendRedirect(ProjectPaths.CATALOG_SERVLET + ProjectPaths.BASE_CATALOG_PARAMETERS);
  }
}
