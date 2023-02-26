package com.task.bookstorewebbapp.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtils {

  private static final String CONTEXT_PATH = "java:/comp/env";
  private static final String DATA_SOURCE_PATH = "jdbc/db";
  private static final String DATA_SOURCE_EXCEPTION_MESSAGE = "Cannot obtain data source";
  private static final String CONNECTION_EXCEPTION_MESSAGE = "Cannot obtain a connection";

  private static DBUtils instance;
  private final DataSource ds;

  private DBUtils() {
    try {
      Context initContext = new InitialContext();
      Context envContext = (Context) initContext.lookup(CONTEXT_PATH);
      ds = (DataSource) envContext.lookup(DATA_SOURCE_PATH);
    } catch (NamingException ex) {
      throw new IllegalStateException(DATA_SOURCE_EXCEPTION_MESSAGE, ex);
    }
  }

  public static synchronized DBUtils getInstance() {
    if (instance == null) {
      instance = new DBUtils();
    }
    return instance;
  }

  public Connection getConnection() {
    Connection con;

    try {
      con = ds.getConnection();
    } catch (SQLException ex) {
      throw new IllegalStateException(CONNECTION_EXCEPTION_MESSAGE, ex);
    }
    return con;
  }

}
