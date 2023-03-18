package com.task.bookstorewebbapp.db.exception;

public class DataSourceException extends RuntimeException{
  public DataSourceException(String message, Exception cause) {
    super(message, cause);
  }

}
