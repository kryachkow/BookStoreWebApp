package com.task.bookstorewebbapp.repository.catalog.impl;

import static org.mockito.Mockito.when;

import com.task.bookstorewebbapp.db.dao.impl.BookDAO;
import com.task.bookstorewebbapp.db.entity.BookEntity;
import com.task.bookstorewebbapp.db.entity.CategoryEntity;
import com.task.bookstorewebbapp.db.entity.PublisherEntity;
import com.task.bookstorewebbapp.model.CatalogFilterDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;
import utils.DBUtilsStaticMock;

@ExtendWith(MockitoExtension.class)
class CatalogRepositoryImplTest {

  private final static List<BookEntity> books = new ArrayList<>();
  @Mock
  private BookDAO bookDAO;
  private final CatalogRepositoryImpl catalogRepository = new CatalogRepositoryImpl();

  @BeforeAll
  static void init() {
    DBUtilsStaticMock.makeDBMock();
    books.add(new BookEntity(1, "A", "B", 23, 100,
        new PublisherEntity(1, "P"),
        new CategoryEntity(1, "C")));
    books.add(new BookEntity(2, "B", "C", 23, 150,
        new PublisherEntity(2, "P"),
        new CategoryEntity(2, "C")));
    books.add(new BookEntity(3, "Author", "kabook", 23, 200,
        new PublisherEntity(3, "P"),
        new CategoryEntity(3, "C")));
    books.add(new BookEntity(1, "U", "Authored", 23, 250,
        new PublisherEntity(2, "P"),
        new CategoryEntity(3, "C")));
  }

  @BeforeEach
  void setUp() throws SQLException {
    Whitebox.setInternalState(catalogRepository, "bookEntityDAO", bookDAO);
    when(bookDAO.getEntities()).thenReturn(books);
  }

  @ParameterizedTest
  @MethodSource("getTestArguments")
  void getBooksByFilter(CatalogFilterDTO catalogFilterDTO, int[] indexesArray)
      throws SQLException {
    List<BookEntity> booksByFilter = catalogRepository.getBooksByFilter(catalogFilterDTO);
    List<BookEntity> expectedResList = new ArrayList<>();
    for(int i : indexesArray){
      expectedResList.add(books.get(i));
    }
    Assertions.assertEquals(indexesArray.length, booksByFilter.size());
    Assertions.assertEquals(expectedResList, booksByFilter);
  }

  static Stream<Arguments> getTestArguments() {
    return Stream.of(
        Arguments.of(new CatalogFilterDTO("BOOKS", 1, 1, 1,
    1, "name", true), new int[]{}),
        Arguments.of(new CatalogFilterDTO("a", 0, 0, 0,
            250, "price", true), new int[]{3,2}),
        Arguments.of(new CatalogFilterDTO("", 0, 2, 0,
            250, "", false), new int[]{3,1}),
        Arguments.of(new CatalogFilterDTO("", 3, 0, 0,
            250, "", true), new int[]{2,3})


    );
  }
}