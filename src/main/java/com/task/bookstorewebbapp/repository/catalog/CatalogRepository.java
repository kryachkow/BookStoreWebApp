package com.task.bookstorewebbapp.repository.catalog;

import com.task.bookstorewebbapp.db.entity.BookEntity;
import com.task.bookstorewebbapp.db.entity.CategoryEntity;
import com.task.bookstorewebbapp.db.entity.PublisherEntity;
import com.task.bookstorewebbapp.db.exception.DAOException;
import com.task.bookstorewebbapp.model.CatalogFilterDTO;
import java.util.List;

public interface CatalogRepository {

  List<BookEntity> getBooksByFilter(CatalogFilterDTO catalogFilterDTO) throws DAOException;
  List<PublisherEntity> getPublishers() throws DAOException;
  List<CategoryEntity> getCategories() throws DAOException;

}
