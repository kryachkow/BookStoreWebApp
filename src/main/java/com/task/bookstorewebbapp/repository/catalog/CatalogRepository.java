package com.task.bookstorewebbapp.repository.catalog;

import com.task.bookstorewebbapp.db.entity.BookEntity;
import com.task.bookstorewebbapp.db.entity.CategoryEntity;
import com.task.bookstorewebbapp.db.entity.PublisherEntity;
import com.task.bookstorewebbapp.db.exception.DataSourceException;
import com.task.bookstorewebbapp.model.CatalogFilterDTO;
import java.sql.SQLException;
import java.util.List;

public interface CatalogRepository {

  List<BookEntity> getBooksByFilter(CatalogFilterDTO catalogFilterDTO) throws DataSourceException, SQLException;
  List<PublisherEntity> getPublishers() throws DataSourceException, SQLException;
  List<CategoryEntity> getCategories() throws DataSourceException, SQLException;

}
