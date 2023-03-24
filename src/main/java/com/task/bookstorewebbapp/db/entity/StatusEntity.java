package com.task.bookstorewebbapp.db.entity;

import java.util.Objects;

public class StatusEntity {

  private long id;
  private String statusName;

  public StatusEntity() {
  }

  public StatusEntity(long id, String statusName) {
    this.id = id;
    this.statusName = statusName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof StatusEntity)) {
      return false;
    }
    StatusEntity that = (StatusEntity) o;
    return getId() == that.getId() && Objects.equals(getStatusName(), that.getStatusName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getStatusName());
  }

  @Override
  public String toString() {
    return "StatusEntity{" +
        "id=" + id +
        ", statusName='" + statusName + '\'' +
        '}';
  }
}
