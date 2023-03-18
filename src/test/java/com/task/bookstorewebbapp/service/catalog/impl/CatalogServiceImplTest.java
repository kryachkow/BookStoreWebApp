package com.task.bookstorewebbapp.service.catalog.impl;

import static org.mockito.Mockito.when;

import com.task.bookstorewebbapp.db.entity.BookEntity;
import com.task.bookstorewebbapp.model.CatalogFilterDTO;
import com.task.bookstorewebbapp.model.PaginationDTO;
import com.task.bookstorewebbapp.repository.catalog.impl.CatalogRepositoryImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.DBUtilsStaticMock;

@ExtendWith(MockitoExtension.class)
class CatalogServiceImplTest {

  private final static List<BookEntity> books = new ArrayList<>();
  @Mock
  private static CatalogRepositoryImpl catalogRepository;

  private static CatalogServiceImpl catalogService;

  @BeforeAll
  static void init() {
    DBUtilsStaticMock.makeDBMock();
    for(int i = 1; i <= 11; i++) {
      BookEntity bookEntity = new BookEntity();
      bookEntity.setId(i);
      books.add(bookEntity);
    }
  }

  @BeforeEach
  void setUp() throws SQLException {
    when(catalogRepository.getPublishers()).thenReturn(null);
    when(catalogRepository.getCategories()).thenReturn(null);
    when(catalogRepository.getBooksByFilter(Mockito.any())).thenReturn(books);
    catalogService = new CatalogServiceImpl(catalogRepository);

  }

  @ParameterizedTest
  @MethodSource("getPaginationArguments")
  void getBooks(PaginationDTO paginationDTO, List<Long> retIdList, int lastPage, int previousPage, int nextPage)
      throws SQLException {
    List<BookEntity> entities = catalogService.getBooks(new CatalogFilterDTO(), paginationDTO);
    Assertions.assertEquals(retIdList, entities.stream().map(BookEntity::getId).collect(Collectors.toList()));
    Assertions.assertEquals(retIdList.size(), entities.size());
    Assertions.assertEquals(lastPage, paginationDTO.getLastPage());
    Assertions.assertEquals(previousPage, paginationDTO.getPreviousPage());
    Assertions.assertEquals(nextPage, paginationDTO.getNextPage());
  }

  static Stream<Arguments> getPaginationArguments() {
    return Stream.of(
        Arguments.of(new PaginationDTO(5, 1, 0, 0,
    0), List.of(1L,2L,3L,4L,5L), 3, -1, 2),
        Arguments.of(new PaginationDTO(5, 2, 0, 0,
            0), List.of(6L,7L,8L,9L,10L), 3, 1, 3),
        Arguments.of(new PaginationDTO(5, 3, 0, 0,
            0), List.of(11L), -1, 2, -1),
        Arguments.of(new PaginationDTO(10, 1, 0, 0,
            0), List.of(1L,2L,3L,4L,5L,6L,7L,8L,9L,10L), 2, -1, 2),
        Arguments.of(new PaginationDTO(10, 2, 0, 0,
            0), List.of(11L), -1, 1, -1)
    );
  }


}