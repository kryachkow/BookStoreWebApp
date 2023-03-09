package com.task.bookstorewebbapp.db.entity;

import java.util.Objects;

public class PublisherEntity {

  private long id;
  private String name;

  public PublisherEntity() {
  }

  public PublisherEntity(long id, String name) {
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
    if (!(o instanceof PublisherEntity)) {
      return false;
    }
    PublisherEntity publisherEntity = (PublisherEntity) o;
    return getId() == publisherEntity.getId() && Objects.equals(getName(), publisherEntity.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName());
  }

  @Override
  public String toString() {
    return "PublisherEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
