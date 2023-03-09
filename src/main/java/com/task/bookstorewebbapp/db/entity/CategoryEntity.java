package com.task.bookstorewebbapp.db.entity;

import java.util.Objects;

public class CategoryEntity {
  private long id;
  private String name;

  public CategoryEntity() {
  }

  public CategoryEntity(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CategoryEntity)) {
      return false;
    }
    CategoryEntity publisher = (CategoryEntity) o;
    return getId() == publisher.getId() && Objects.equals(getName(), publisher.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName());
  }

  @Override
  public String toString() {
    return "CategoryEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }

}
