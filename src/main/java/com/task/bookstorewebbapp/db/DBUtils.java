package com.task.bookstorewebbapp.db;

import com.task.bookstorewebbapp.db.exception.DataSourceException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DBUtils {

  private static final String CONTEXT_PATH = "java:/comp/env";
  private static final String DATA_SOURCE_PATH = "jdbc/db";
  private static final String DATA_SOURCE_EXCEPTION_MESSAGE = "Cannot obtain data source";
  private static final String CONNECTION_EXCEPTION_MESSAGE = "Cannot obtain a connection";

  private static final Logger LOGGER = LogManager.getLogger(DBUtils.class.getName());

  private static DBUtils instance;
  private final DataSource ds;

  private DBUtils() {
    try {
      Context initContext = new InitialContext();
      Context envContext = (Context) initContext.lookup(CONTEXT_PATH);
      ds = (DataSource) envContext.lookup(DATA_SOURCE_PATH);
    } catch (NamingException ex) {
      LOGGER.error(DATA_SOURCE_EXCEPTION_MESSAGE, ex);
      throw new DataSourceException(DATA_SOURCE_EXCEPTION_MESSAGE, ex);
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
      LOGGER.error(CONNECTION_EXCEPTION_MESSAGE, ex);
      throw new DataSourceException(CONNECTION_EXCEPTION_MESSAGE, ex);
    }
    return con;
  }

}
