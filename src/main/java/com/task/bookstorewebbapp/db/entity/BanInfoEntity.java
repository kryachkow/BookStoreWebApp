package com.task.bookstorewebbapp.db.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class BanInfoEntity {

  private long userId;
  private int logCount;
  private LocalDateTime dateTime;

  public BanInfoEntity() {
  }

  public BanInfoEntity(long userId, int logCount, LocalDateTime dateTime) {
    this.userId = userId;
    this.logCount = logCount;
    this.dateTime = dateTime;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public int getLogCount() {
    return logCount;
  }

  public void setLogCount(int logCount) {
    this.logCount = logCount;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BanInfoEntity)) {
      return false;
    }
    BanInfoEntity that = (BanInfoEntity) o;
    return getUserId() == that.getUserId() && getLogCount() == that.getLogCount()
        && Objects.equals(getDateTime(), that.getDateTime());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUserId(), getLogCount(), getDateTime());
  }
}
