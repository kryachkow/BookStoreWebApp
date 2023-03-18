package com.task.bookstorewebbapp.service.catalog.impl;

import com.task.bookstorewebbapp.db.entity.BookEntity;
import com.task.bookstorewebbapp.db.entity.CategoryEntity;
import com.task.bookstorewebbapp.db.entity.PublisherEntity;
import com.task.bookstorewebbapp.model.CatalogFilterDTO;
import com.task.bookstorewebbapp.model.PaginationDTO;
import com.task.bookstorewebbapp.repository.catalog.CatalogRepository;
import com.task.bookstorewebbapp.service.catalog.CatalogService;
import com.task.bookstorewebbapp.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class CatalogServiceImpl implements CatalogService {

  private static final int DEFAULT_MIN_PRICE = 0;
  private static final int DEFAULT_MAX_PRICE = 10000;
  private final CatalogRepository catalogRepository;
  private final List<PublisherEntity> publisherEntities;
  private final List<CategoryEntity> categoryEntities;


  public CatalogServiceImpl(CatalogRepository catalogRepository) throws SQLException {
    this.catalogRepository = catalogRepository;
    publisherEntities = this.catalogRepository.getPublishers();
    categoryEntities = this.catalogRepository.getCategories();
  }

  @Override
  public List<BookEntity> getBooks(CatalogFilterDTO catalogFilterDTO, PaginationDTO paginationDTO)
      throws SQLException {
    List<BookEntity> bookEntities = catalogRepository.getBooksByFilter(catalogFilterDTO);
    int lastIndex = getLastIndex(paginationDTO, bookEntities.size());
    paginationDTO.setPreviousPage(paginationDTO.getPageNumber() == 1
        ? -1 : paginationDTO.getPageNumber() - 1);
    return bookEntities.subList(
        ((paginationDTO.getPageNumber() - 1) * paginationDTO.getPageSize())
        , lastIndex);
  }

  @Override
  public List<PublisherEntity> getPublishers() {
    return publisherEntities;
  }

  @Override
  public List<CategoryEntity> getCategories() {
    return categoryEntities;
  }


  @Override
  public CatalogFilterDTO getCatalogFilterDTO(HttpServletRequest request) {
    CatalogFilterDTO catalogFilterDTO = new CatalogFilterDTO();
    catalogFilterDTO.setTitleSearch(request.getParameter(Constants.TITLE_SEARCH));
    manageIds(request, catalogFilterDTO);
    managePrices(request, catalogFilterDTO);
    catalogFilterDTO.setSorting(request.getParameter(Constants.SORTING));
    catalogFilterDTO.setInverted(Boolean.parseBoolean(request.getParameter(Constants.INVERTED)));

    return catalogFilterDTO;
  }

  @Override
  public PaginationDTO getPaginatingDTO(HttpServletRequest request) {
    PaginationDTO paginationDTO = new PaginationDTO();
    int pageSize = Integer.parseInt(request.getParameter(Constants.PAGE_SIZE));
    int pageNumber = Integer.parseInt(request.getParameter(Constants.PAGE_NUMBER));
    paginationDTO.setPageNumber(Math.max(pageNumber, 1));
    paginationDTO.setPageSize(Math.max(pageSize, 5));
    return paginationDTO;
  }

  private int getLastIndex(PaginationDTO paginationDTO, int listSize) {
    if (paginationDTO.getPageNumber() * paginationDTO.getPageSize() >= listSize) {
      paginationDTO.setLastPage(-1);
      paginationDTO.setNextPage(-1);
      return listSize;
    }
    paginationDTO.setLastPage((listSize / paginationDTO.getPageSize()) + 1);
    paginationDTO.setNextPage(paginationDTO.getPageNumber() + 1);
    return paginationDTO.getPageSize() * paginationDTO.getPageNumber();
  }


  private void manageIds(HttpServletRequest request, CatalogFilterDTO catalogFilterDTO) {
    long publisherId = Long.parseLong(request.getParameter(Constants.PUBLISHER_ID));
    catalogFilterDTO.setPublisherId(
        publisherEntities.stream().map(PublisherEntity::getId).anyMatch(l -> l.equals(publisherId))
            ? publisherId : 0
    );
    long categoryId = Long.parseLong(request.getParameter(Constants.CATEGORY_ID));
    catalogFilterDTO.setCategoryId(
        categoryEntities.stream().map(CategoryEntity::getId).anyMatch(l -> l.equals(categoryId))
            ? categoryId : 0
    );
  }

  private void managePrices(HttpServletRequest request, CatalogFilterDTO catalogFilterDTO) {
    int minPrice = Integer.parseInt(request.getParameter(Constants.MINIMUM_PRICE));
    int maxPrice = Integer.parseInt(request.getParameter(Constants.MAXIMUM_PRICE));
    if (minPrice < 0 || minPrice > maxPrice) {
      minPrice = DEFAULT_MIN_PRICE;
    }
    if (maxPrice < 0) {
      maxPrice = DEFAULT_MAX_PRICE;
    }
    catalogFilterDTO.setMinPrice(minPrice);
    catalogFilterDTO.setMaxPrice(maxPrice);
  }
}
