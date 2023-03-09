package com.task.bookstorewebbapp.model;

import java.util.Objects;

public class PaginationDTO {
  private int pageSize;
  private int pageNumber;
  private int lastPage;
  private int previousPage;
  private int nextPage;

  public PaginationDTO() {
  }

  public PaginationDTO(int pageSize, int pageNumber, int lastPage, int previousPage,
      int nextPage) {
    this.pageSize = pageSize;
    this.pageNumber = pageNumber;
    this.lastPage = lastPage;
    this.previousPage = previousPage;
    this.nextPage = nextPage;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public int getLastPage() {
    return lastPage;
  }

  public void setLastPage(int lastPage) {
    this.lastPage = lastPage;
  }

  public int getPreviousPage() {
    return previousPage;
  }

  public void setPreviousPage(int previousPage) {
    this.previousPage = previousPage;
  }

  public int getNextPage() {
    return nextPage;
  }

  public void setNextPage(int nextPage) {
    this.nextPage = nextPage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PaginationDTO)) {
      return false;
    }
    PaginationDTO that = (PaginationDTO) o;
    return getPageSize() == that.getPageSize() && getPageNumber() == that.getPageNumber()
        && getLastPage() == that.getLastPage()
        && getPreviousPage() == that.getPreviousPage() && getNextPage() == that.getNextPage();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPageSize(), getPageNumber(), getLastPage(),
        getPreviousPage(), getNextPage());
  }

  @Override
  public String toString() {
    return "PaginationDTO{" +
        "pageSize=" + pageSize +
        ", pageNumber=" + pageNumber +
        ", lastPage=" + lastPage +
        ", previousPage=" + previousPage +
        ", nextPage=" + nextPage +
        '}';
  }
}
