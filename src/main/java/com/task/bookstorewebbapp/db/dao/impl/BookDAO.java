package com.task.bookstorewebbapp.db.dao.impl;

import com.task.bookstorewebbapp.db.DBUtils;
import com.task.bookstorewebbapp.db.SearchField;
import com.task.bookstorewebbapp.db.dao.DAO;
import com.task.bookstorewebbapp.db.entity.BookEntity;
import com.task.bookstorewebbapp.db.entity.CategoryEntity;
import com.task.bookstorewebbapp.db.entity.PublisherEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BookDAO implements DAO<BookEntity> {

  private static final String ID = "id";
  private static final String AUTHOR = "author";
  private static final String BOOK_TITLE = "book_title";
  private static final String PAGE_NUMBER = "page_number";
  private static final String PRICE = "price";
  private static final String PUBLISHER_ID = "publisher_id";
  private static final String PUBLISHER_NAME = "publisher";
  private static final String CATEGORY_ID = "category_id";
  private static final String CATEGORY_NAME = "category";

  private static final String SELECT_BOOK = "SELECT books.*, publishers.publisher, categories.category FROM book_store.books LEFT JOIN publishers ON books.publisher_id = publishers.id LEFT JOIN categories ON books.category_id = categories.id WHERE %s = ?";
  private static final String SELECT_ALL_BOOKS_STATEMENT = "SELECT books.*, publishers.publisher, categories.category FROM book_store.books LEFT JOIN publishers ON books.publisher_id = publishers.id LEFT JOIN categories ON books.category_id = categories.id";
  private static final Logger LOGGER = LogManager.getLogger(BookDAO.class.getName());

  private final DBUtils connectionSupplier = DBUtils.getInstance();


  @Override
  public <V> BookEntity getEntityByField(SearchField<V> fieldValue) throws SQLException {
    BookEntity bookEntity;
    try(Connection con = connectionSupplier.getConnection();
    PreparedStatement ps = con.prepareStatement(String.format(SELECT_BOOK, fieldValue.getName()))) {
      ps.setObject(1, fieldValue.getValue());
      ResultSet rs = ps.executeQuery();
      if(rs.next()) {
        bookEntity = mapToBookEntity(rs);
      } else {
        throw new SQLException("There is no book with such" + fieldValue.getName());
      }
    } catch (SQLException e) {
      LOGGER.warn("Cannot select book by " + fieldValue.getName(), e);
      throw new SQLException("Cannot select book by" + fieldValue.getName(), e);
    }
    return bookEntity;
  }

  @Override
  public <V> List<BookEntity> getEntitiesByField(V fieldValue) throws SQLException {
    throw new UnsupportedOperationException();

  }

  @Override
  public List<BookEntity> getEntities() throws SQLException {
    List<BookEntity> entities = new ArrayList<>();
    try (Connection con = connectionSupplier.getConnection();
        PreparedStatement ps = con.prepareStatement(SELECT_ALL_BOOKS_STATEMENT)) {
      ResultSet resultSet = ps.executeQuery();
      while (resultSet.next()) {
        entities.add(mapToBookEntity(resultSet));
      }
    } catch (SQLException e) {
      LOGGER.error("Can't retrieve books from database " + e.getMessage(), e);
      throw new SQLException("Can't retrieve books from database " + e.getMessage(), e);
    }
    return entities;
  }

  @Override
  public long insertEntity(BookEntity entity) throws SQLException {
    throw new UnsupportedOperationException();

  }

  @Override
  public boolean updateEntity(BookEntity entity) throws SQLException {
    throw new UnsupportedOperationException();

  }

  @Override
  public boolean deleteEntity(BookEntity entity) throws SQLException {
    throw new UnsupportedOperationException();

  }

  private BookEntity mapToBookEntity(ResultSet resultSet) throws SQLException {
    BookEntity bookEntity = new BookEntity();
    bookEntity.setId(resultSet.getLong(ID));
    bookEntity.setAuthor(resultSet.getString(AUTHOR));
    bookEntity.setBookTitle(resultSet.getString(BOOK_TITLE));
    bookEntity.setPageNumber(resultSet.getInt(PAGE_NUMBER));
    bookEntity.setPrice(resultSet.getInt(PRICE));
    bookEntity.setPublisherEntity(
        new PublisherEntity(resultSet.getLong(PUBLISHER_ID), resultSet.getString(PUBLISHER_NAME)));
    bookEntity.setCategoryEntity(
        new CategoryEntity(resultSet.getLong(CATEGORY_ID), resultSet.getString(CATEGORY_NAME)));

    return bookEntity;
  }
}
