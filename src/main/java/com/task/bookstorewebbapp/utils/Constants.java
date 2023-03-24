package com.task.bookstorewebbapp.utils;

public final class Constants {

  public static final String USER_ATTRIBUTE = "user";
  public static final String EMAIL_NOT_VALID = "Email isn`t valid";
  public static final String NAME_NOT_VALID = "Name isn`t valid";
  public static final String SURNAME_NOT_VALID = "Surname isn`t valid";
  public static final String NICK_NAME_NOT_VALID = "Nickname isn`t valid";
  public static final String PASSWORD_NOT_VALID = "Password isn`t valid";
  public static final String EMAIL_EXISTS = "Such email exists";
  public static final String NICKNAME_EXISTS = "Such nickname exists";
  public static final String DATABASE_ERROR = "Error occurred on database layer, try to repeat after";
  public static final String CAPTCHA_IMAGE_ATTRIBUTE = "captchaImage";
  public static final String CAPTCHA_ID_ATTRIBUTE = "captchaId";
  public static final int FILE_SIZE_THRESHOLD = 1024 * 1024;
  public static final int MAX_FILE_SIZE = 1024 * 1024 * 10;
  public static final int MAX_REQUEST_SIZE = 1024 * 1024 * 100;

  public static final String TITLE_SEARCH = "titleSearch";
  public static final String CATEGORY_ID = "categoryId";
  public static final String PUBLISHER_ID = "publisherId";
  public static final String MINIMUM_PRICE = "minPrice";
  public static final String MAXIMUM_PRICE = "maxPrice";
  public static final String SORTING = "sorting";
  public static final String INVERTED = "inverted";
  public static final String PAGE_NUMBER = "pageNumber";
  public static final String PAGE_SIZE = "pageSize";
  public static final String CART_ATTRIBUTE = "cart";
  public static final int MIN_CATALOG_PARAMS = 7;
  public static final int MIN_ADD_TO_CART_PARAMS = 9;
  public static final String BOOK_ID = "bookId";
  public static final String QUANTITY = "quantity";
  public static final String ERROR_ATTRIBUTE = "error";
  public static final String PAYMENT_TYPE_ID = "paymentTypeId";
  public static final String PAYMENT_DETAILS = "paymentDetails";


  private Constants() {
  }
}
