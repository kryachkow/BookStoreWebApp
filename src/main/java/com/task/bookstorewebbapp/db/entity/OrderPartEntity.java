package com.task.bookstorewebbapp.db.entity;

import java.util.Objects;

public final class OrderPartEntity {

  private final BookEntity bookEntity;
  private final int quantity;


  public OrderPartEntity(BookEntity bookEntity, int quantity) {
    this.bookEntity = new BookEntity();
    copyBookEntity(bookEntity, this.bookEntity);
    this.quantity = quantity;
  }

  public BookEntity getBookEntity() {
    BookEntity returnBook = new BookEntity();
    copyBookEntity(this.bookEntity, returnBook);
    return returnBook;
  }

  public int getQuantity() {
    return quantity;
  }

  private void copyBookEntity(BookEntity bookToCopy, BookEntity targetBook) {
    targetBook.setId(bookToCopy.getId());
    targetBook.setAuthor(bookToCopy.getAuthor());
    targetBook.setBookTitle(bookToCopy.getBookTitle());
    targetBook.setPageNumber(bookToCopy.getPageNumber());
    targetBook.setPrice(bookToCopy.getPrice());

    PublisherEntity publisherEntity = new PublisherEntity();
    publisherEntity.setId(bookToCopy.getPublisherEntity().getId());
    publisherEntity.setName(bookToCopy.getPublisherEntity().getName());

    CategoryEntity categoryEntity = new CategoryEntity();
    categoryEntity.setId(bookToCopy.getCategoryEntity().getId());
    categoryEntity.setName(bookToCopy.getCategoryEntity().getName());
    targetBook.setPublisherEntity(publisherEntity);
    targetBook.setCategoryEntity(categoryEntity);

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof OrderPartEntity)) {
      return false;
    }
    OrderPartEntity that = (OrderPartEntity) o;
    return getQuantity() == that.getQuantity() && Objects.equals(getBookEntity(),
        that.getBookEntity());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getBookEntity(), getQuantity());
  }

  @Override
  public String toString() {
    return "OrderPartEntity{" +
        "bookEntity=" + bookEntity +
        ", quantity=" + quantity +
        '}';
  }
}
