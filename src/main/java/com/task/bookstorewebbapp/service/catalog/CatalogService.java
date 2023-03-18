package com.task.bookstorewebbapp.service.catalog;

import com.task.bookstorewebbapp.db.entity.BookEntity;
import com.task.bookstorewebbapp.db.entity.CategoryEntity;
import com.task.bookstorewebbapp.db.entity.PublisherEntity;
import com.task.bookstorewebbapp.db.exception.DataSourceException;
import com.task.bookstorewebbapp.model.CatalogFilterDTO;
import com.task.bookstorewebbapp.model.PaginationDTO;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public interface CatalogService {

  List<PublisherEntity> getPublishers();
  List<CategoryEntity> getCategories();
  List<BookEntity> getBooks(CatalogFilterDTO catalogFilterDTO, PaginationDTO paginationDTO)
      throws DataSourceException, SQLException;
  CatalogFilterDTO getCatalogFilterDTO(HttpServletRequest request);
  PaginationDTO getPaginatingDTO(HttpServletRequest request);

}
