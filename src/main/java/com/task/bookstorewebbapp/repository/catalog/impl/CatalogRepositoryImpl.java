package com.task.bookstorewebbapp.repository.catalog.impl;

import com.task.bookstorewebbapp.db.dao.DAO;
import com.task.bookstorewebbapp.db.dao.impl.BookDAO;
import com.task.bookstorewebbapp.db.dao.impl.CategoryDAO;
import com.task.bookstorewebbapp.db.dao.impl.PublisherDAO;
import com.task.bookstorewebbapp.db.entity.BookEntity;
import com.task.bookstorewebbapp.db.entity.CategoryEntity;
import com.task.bookstorewebbapp.db.entity.PublisherEntity;
import com.task.bookstorewebbapp.model.CatalogFilterDTO;
import com.task.bookstorewebbapp.repository.catalog.CatalogRepository;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class CatalogRepositoryImpl implements CatalogRepository {

  private final DAO<BookEntity> bookEntityDAO = new BookDAO();
  private final DAO<PublisherEntity> publisherEntityDAO = new PublisherDAO();
  private final DAO<CategoryEntity> categoryEntityDAO = new CategoryDAO();

  private final Map<String, Comparator<BookEntity>> sortingComparators = new HashMap<>();


  public CatalogRepositoryImpl() {
    sortingComparators.put(
        "bookTitle", Comparator.comparing(BookEntity::getBookTitle)
    );
    sortingComparators.put(
        "price", Comparator.comparingInt(BookEntity::getPrice)
    );
    sortingComparators.put(
        "author", Comparator.comparing(BookEntity::getAuthor)
    );
  }


  @Override
  public List<BookEntity> getBooksByFilter(CatalogFilterDTO catalogFilterDTO)
      throws SQLException {
    return bookEntityDAO.getEntities().stream()
        .filter(e -> catalogFilterDTO.getTitleSearch() == null ||
            e.getBookTitle().toLowerCase(Locale.ROOT).contains(catalogFilterDTO.getTitleSearch().toLowerCase()))
        .filter(e -> catalogFilterDTO.getCategoryId() <= 0
            || e.getCategoryEntity().getId() == catalogFilterDTO.getCategoryId())
        .filter(e -> catalogFilterDTO.getPublisherId() <= 0
            || e.getPublisherEntity().getId() == catalogFilterDTO.getPublisherId())
        .filter(e -> e.getPrice() >= catalogFilterDTO.getMinPrice()
            && e.getPrice() <= catalogFilterDTO.getMaxPrice())
        .sorted(catalogFilterDTO.isInverted()
            ? sortingComparators.getOrDefault(catalogFilterDTO.getSorting(), Comparator.comparing(BookEntity::getBookTitle)).reversed()
            : sortingComparators.getOrDefault(catalogFilterDTO.getSorting(), Comparator.comparing(BookEntity::getBookTitle)))
        .collect(Collectors.toList());
  }

  @Override
  public List<PublisherEntity> getPublishers() throws SQLException {
    return publisherEntityDAO.getEntities();
  }

  @Override
  public List<CategoryEntity> getCategories() throws SQLException {
    return categoryEntityDAO.getEntities();
  }
}
