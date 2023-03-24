package com.task.bookstorewebbapp.db.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrderEntity {

  private long id;
  private String statusDescription;
  private LocalDateTime dateTime;
  private long userId;
  private String paymentDetails;
  private List<OrderPartEntity> orderParts;
  private StatusEntity statusEntity;
  private PaymentTypeEntity paymentTypeEntity;

  public OrderEntity() {
  }

  public OrderEntity(long id, String statusDescription, LocalDateTime dateTime, long userId,
      String paymentDetails, List<OrderPartEntity> orderParts, StatusEntity statusEntity,
      PaymentTypeEntity paymentTypeEntity) {
    this.id = id;
    this.statusDescription = statusDescription;
    this.dateTime = dateTime;
    this.userId = userId;
    this.paymentDetails = paymentDetails;
    this.orderParts = orderParts;
    this.statusEntity = statusEntity;
    this.paymentTypeEntity = paymentTypeEntity;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getStatusDescription() {
    return statusDescription;
  }

  public void setStatusDescription(String statusDescription) {
    this.statusDescription = statusDescription;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getPaymentDetails() {
    return paymentDetails;
  }

  public void setPaymentDetails(String paymentDetails) {
    this.paymentDetails = paymentDetails;
  }

  public List<OrderPartEntity> getOrderParts() {
    return orderParts;
  }

  public void setOrderParts(List<OrderPartEntity> orderParts) {
    this.orderParts = orderParts;
  }

  public StatusEntity getStatusEntity() {
    return statusEntity;
  }

  public void setStatusEntity(StatusEntity statusEntity) {
    this.statusEntity = statusEntity;
  }

  public PaymentTypeEntity getPaymentTypeEntity() {
    return paymentTypeEntity;
  }

  public void setPaymentTypeEntity(PaymentTypeEntity paymentTypeEntity) {
    this.paymentTypeEntity = paymentTypeEntity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof OrderEntity)) {
      return false;
    }
    OrderEntity that = (OrderEntity) o;
    return getId() == that.getId() && getUserId() == that.getUserId() && Objects.equals(
        getStatusDescription(), that.getStatusDescription()) && Objects.equals(
        getDateTime(), that.getDateTime()) && Objects.equals(getPaymentDetails(),
        that.getPaymentDetails()) && Objects.equals(getOrderParts(), that.getOrderParts())
        && Objects.equals(getStatusEntity(), that.getStatusEntity())
        && Objects.equals(getPaymentTypeEntity(), that.getPaymentTypeEntity());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getStatusDescription(), getDateTime(), getUserId(),
        getPaymentDetails(), getOrderParts(), getStatusEntity(), getPaymentTypeEntity());
  }

  @Override
  public String toString() {
    return "OrderEntity{" +
        "id=" + id +
        ", statusDescription='" + statusDescription + '\'' +
        ", dateTime=" + dateTime +
        ", userId=" + userId +
        ", paymentDetails='" + paymentDetails + '\'' +
        ", orderParts=" + orderParts +
        ", statusEntity=" + statusEntity +
        ", paymentTypeEntity=" + paymentTypeEntity +
        '}';
  }
}
