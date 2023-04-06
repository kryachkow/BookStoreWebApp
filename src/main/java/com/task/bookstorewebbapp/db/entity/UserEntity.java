package com.task.bookstorewebbapp.db.entity;

import java.util.Objects;

public class UserEntity {

  private long id;
  private String email;
  private String name;
  private String surname;
  private String nickname;
  private String password;
  private boolean mailingSubscription;
  private long roleId;

  public UserEntity() {
  }

  public UserEntity(long id, String email, String name, String surname, String nickname,
      String password, boolean mailingSubscription, long roleId) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.surname = surname;
    this.nickname = nickname;
    this.password = password;
    this.mailingSubscription = mailingSubscription;
    this.roleId = roleId;
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

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
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

  public long getRoleId() {
    return roleId;
  }

  public void setRoleId(long roleId) {
    this.roleId = roleId;
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
        && getRoleId() == that.getRoleId() && Objects.equals(getEmail(), that.getEmail())
        && Objects.equals(getName(), that.getName()) && Objects.equals(
        getSurname(), that.getSurname()) && Objects.equals(getNickname(),
        that.getNickname()) && Objects.equals(getPassword(), that.getPassword());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getEmail(), getName(), getSurname(), getNickname(), getPassword(),
        isMailingSubscription(), getRoleId());
  }
}
