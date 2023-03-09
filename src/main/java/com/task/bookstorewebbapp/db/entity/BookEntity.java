package com.task.bookstorewebbapp.db.entity;

import java.util.Objects;

public class BookEntity {

  private long id;
  private String author;
  private String bookTitle;
  private int pageNumber;
  private int price;
  private PublisherEntity publisherEntity;
  private CategoryEntity categoryEntity;

  public BookEntity() {
  }

  public BookEntity(long id, String author, String bookTitle, int pageNumber, int price,
      PublisherEntity publisherEntity, CategoryEntity categoryEntity) {
    this.id = id;
    this.author = author;
    this.bookTitle = bookTitle;
    this.pageNumber = pageNumber;
    this.price = price;
    this.publisherEntity = publisherEntity;
    this.categoryEntity = categoryEntity;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getBookTitle() {
    return bookTitle;
  }

  public void setBookTitle(String bookTitle) {
    this.bookTitle = bookTitle;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public PublisherEntity getPublisherEntity() {
    return publisherEntity;
  }

  public void setPublisherEntity(PublisherEntity publisherEntity) {
    this.publisherEntity = publisherEntity;
  }

  public CategoryEntity getCategoryEntity() {
    return categoryEntity;
  }

  public void setCategoryEntity(CategoryEntity categoryEntity) {
    this.categoryEntity = categoryEntity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BookEntity)) {
      return false;
    }
    BookEntity that = (BookEntity) o;
    return getId() == that.getId() && getPageNumber() == that.getPageNumber()
        && getPrice() == that.getPrice() && Objects.equals(getAuthor(), that.getAuthor())
        && Objects.equals(getBookTitle(), that.getBookTitle()) && Objects.equals(
        getPublisherEntity(), that.getPublisherEntity()) && Objects.equals(
        getCategoryEntity(), that.getCategoryEntity());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getAuthor(), getBookTitle(), getPageNumber(), getPrice(),
        getPublisherEntity(), getCategoryEntity());
  }

  @Override
  public String toString() {
    return "BookEntity{" +
        "id=" + id +
        ", author='" + author + '\'' +
        ", bookTitle='" + bookTitle + '\'' +
        ", pageNumber=" + pageNumber +
        ", price=" + price +
        ", publisherEntity=" + publisherEntity +
        ", categoryEntity=" + categoryEntity +
        '}';
  }
}
