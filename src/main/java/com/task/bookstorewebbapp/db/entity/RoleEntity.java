package com.task.bookstorewebbapp.db.entity;

import java.util.Objects;

public class RoleEntity {

  private long id;
  private String name;

  public RoleEntity() {
  }

  public RoleEntity(long id, String name) {
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
    if (!(o instanceof RoleEntity)) {
      return false;
    }
    RoleEntity that = (RoleEntity) o;
    return getId() == that.getId() && Objects.equals(getName(), that.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName());
  }
}
