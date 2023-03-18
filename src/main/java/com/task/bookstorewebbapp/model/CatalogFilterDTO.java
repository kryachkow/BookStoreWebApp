package com.task.bookstorewebbapp.model;

import java.util.Objects;

public class CatalogFilterDTO {

  private String titleSearch;
  private long categoryId;
  private long publisherId;
  private int minPrice;
  private int maxPrice;
  private String sorting;

  private boolean inverted;

  public CatalogFilterDTO() {
  }

  public CatalogFilterDTO(String titleSearch, long categoryId, long publisherId, int minPrice,
      int maxPrice, String sorting, boolean inverted) {
    this.titleSearch = titleSearch;
    this.categoryId = categoryId;
    this.publisherId = publisherId;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
    this.sorting = sorting;
    this.inverted = inverted;
  }

  public String getTitleSearch() {
    return titleSearch;
  }

  public void setTitleSearch(String titleSearch) {
    this.titleSearch = titleSearch;
  }

  public long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(long categoryId) {
    this.categoryId = categoryId;
  }

  public long getPublisherId() {
    return publisherId;
  }

  public void setPublisherId(long publisherId) {
    this.publisherId = publisherId;
  }

  public int getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(int minPrice) {
    this.minPrice = minPrice;
  }

  public int getMaxPrice() {
    return maxPrice;
  }

  public void setMaxPrice(int maxPrice) {
    this.maxPrice = maxPrice;
  }

  public String getSorting() {
    return sorting;
  }

  public void setSorting(String sorting) {
    this.sorting = sorting;
  }

  public boolean isInverted() {
    return inverted;
  }

  public void setInverted(boolean inverted) {
    this.inverted = inverted;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CatalogFilterDTO)) {
      return false;
    }
    CatalogFilterDTO that = (CatalogFilterDTO) o;
    return getCategoryId() == that.getCategoryId() && getPublisherId() == that.getPublisherId()
        && getMinPrice() == that.getMinPrice() && getMaxPrice() == that.getMaxPrice()
        && isInverted() == that.isInverted() && Objects.equals(getTitleSearch(),
        that.getTitleSearch()) && Objects.equals(getSorting(), that.getSorting());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTitleSearch(), getCategoryId(), getPublisherId(), getMinPrice(),
        getMaxPrice(), getSorting(), isInverted());
  }

  @Override
  public String toString() {
    return "CatalogFilterDTO{" +
        "nameSearch='" + titleSearch + '\'' +
        ", categoryId=" + categoryId +
        ", publisherId=" + publisherId +
        ", minPrice=" + minPrice +
        ", maxPrice=" + maxPrice +
        ", sorting='" + sorting + '\'' +
        ", inverted=" + inverted +
        '}';
  }
}
