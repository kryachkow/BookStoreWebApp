package com.task.bookstorewebbapp.entity;

import java.util.Objects;

public class UserEntity {

  private long id;
  private String email;
  private String name;
  private String surname;
  private String nickName;
  private String password;
  private boolean mailingSubscription;

  public UserEntity() {
  }


  public UserEntity(long id, String email, String name, String surname, String nickName,
      String password, boolean mailingSubscription) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.surname = surname;
    this.nickName = nickName;
    this.password = password;
    this.mailingSubscription = mailingSubscription;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isMailingSubscription() {
    return mailingSubscription;
  }

  public void setMailingSubscription(boolean mailingSubscription) {
    this.mailingSubscription = mailingSubscription;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserEntity)) {
      return false;
    }
    UserEntity that = (UserEntity) o;
    return getId() == that.getId() && isMailingSubscription() == that.isMailingSubscription()
        && getEmail().equals(that.getEmail()) && getName().equals(that.getName())
        && getSurname().equals(that.getSurname()) && getNickName().equals(that.getNickName())
        && getPassword().equals(that.getPassword());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getEmail(), getName(), getSurname(), getNickName(), getPassword(),
        isMailingSubscription());
  }

  @Override
  public String toString() {
    return "UserEntity{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", name='" + name + '\'' +
        ", surname='" + surname + '\'' +
        ", nickName='" + nickName + '\'' +
        ", password='" + password + '\'' +
        ", mailingSubscription=" + mailingSubscription +
        '}';
  }
}
