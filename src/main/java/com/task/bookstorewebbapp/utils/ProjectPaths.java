package com.task.bookstorewebbapp.utils;

public final class ProjectPaths {

  public static final String INDEX_JSP = "index.jsp";
  public static final String REGISTER_JSP = "../WEB-INF/view/signUp.jsp";

  public static final String ADMIN_JSP = "../WEB-INF/view/admin.jsp";
  public static final String LOG_OUT_SERVLET = "user/logOut";
  public static final String SING_IN_JSP = "../WEB-INF/view/signIn.jsp";
  public static final String CART_JSP = "WEB-INF/view/cart.jsp";
  public static final String CATALOG_JSP = "WEB-INF/view/catalog.jsp";
  public static final String ERROR_JSP = "error.jsp";
  public static final String ERROR_NOT_FOUND_JSP = "error404.jsp";
  public static final String SIGN_UP_SERVLET = "guest/signUp";
  public static final String CATALOG_SERVLET = "catalog";
  public static final String BASE_CATALOG_PARAMETERS = "?categoryId=0&publisherId=0&minPrice=0&maxPrice=10000&sorting=bookTitle&inverted=false&pageNumber=1&pageSize=5";
  public static final String SIGN_IN_SERVLET = "guest/signIn";
  public static final String AVATAR_CATALOG_PATH = "C:\\Users\\Admin\\Documents\\BookStoreWebAppAvatars";
  public static final String CART_SERVLET = "cart";

  public static final String ERROR_403 = "error403.jsp";



  private ProjectPaths(){}
}
