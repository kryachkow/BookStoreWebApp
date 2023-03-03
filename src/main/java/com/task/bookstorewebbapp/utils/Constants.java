package com.task.bookstorewebbapp.utils;

public final class  Constants {
  public static final String USER_ATTRIBUTE = "user";
  public static final String EMAIL_NOT_VALID = "Email isn`t valid";
  public static final String NAME_NOT_VALID = "Name isn`t valid";
  public static final String SURNAME_NOT_VALID = "Surname isn`t valid";
  public static final String NICK_NAME_NOT_VALID = "Nickname isn`t valid";
  public static final  String PASSWORD_NOT_VALID = "Password isn`t valid";
  public static final String EMAIL_EXISTS = "Such email exists";
  public static final String NICKNAME_EXISTS = "Such nickname exists";
  public static final String DATABASE_ERROR = "Error occurred on database layer, try to repeat after";
  public static final String CAPTCHA_IMAGE_ATTRIBUTE = "captchaImage";
  public static final String CAPTCHA_ID_ATTRIBUTE = "captchaId";
  public static final int FILE_SIZE_THRESHOLD = 1024 * 1024;
  public static final int MAX_FILE_SIZE = 1024 * 1024 * 10;
  public static final int MAX_REQUEST_SIZE = 1024 * 1024 * 100;

  private Constants(){}
}
