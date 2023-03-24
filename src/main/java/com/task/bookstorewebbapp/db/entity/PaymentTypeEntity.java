package com.task.bookstorewebbapp.db.entity;

import java.util.Objects;

public class PaymentTypeEntity {

  private long id;
  private String paymentType;

  public PaymentTypeEntity() {
  }

  public PaymentTypeEntity(long id, String paymentType) {
    this.id = id;
    this.paymentType = paymentType;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PaymentTypeEntity)) {
      return false;
    }
    PaymentTypeEntity that = (PaymentTypeEntity) o;
    return getId() == that.getId() && Objects.equals(getPaymentType(),
        that.getPaymentType());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getPaymentType());
  }

  @Override
  public String toString() {
    return "PaymentTypeEntity{" +
        "id=" + id +
        ", paymentType='" + paymentType + '\'' +
        '}';
  }
}
