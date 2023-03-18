package com.task.bookstorewebbapp.model;

import java.util.Objects;

public class UserFormDTO {

  private String email;
  private String name;
  private String surname;
  private String nickname;
  private String password;
  private String repeatPassword;
  private boolean mailingSubscription;


  public UserFormDTO() {
  }

  public UserFormDTO(String email, String name, String surname, String nickname,
      String password, String repeatPassword, boolean mailingSubscription) {
    this.email = email;
    this.name = name;
    this.surname = surname;
    this.nickname = nickname;
    this.password = password;
    this.repeatPassword = repeatPassword;
    this.mailingSubscription = mailingSubscription;
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

  public String getRepeatPassword() {
    return repeatPassword;
  }

  public void setRepeatPassword(String repeatPassword) {
    this.repeatPassword = repeatPassword;
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
    if (!(o instanceof UserFormDTO)) {
      return false;
    }
    UserFormDTO that = (UserFormDTO) o;
    return isMailingSubscription() == that.isMailingSubscription() && getEmail().equals(
        that.getEmail()) && getName().equals(that.getName()) && getSurname().equals(
        that.getSurname())
        && getNickname().equals(that.getNickname()) && getPassword().equals(that.getPassword())
        && getRepeatPassword().equals(that.getRepeatPassword());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getEmail(), getName(), getSurname(), getNickname(), getPassword(),
        getRepeatPassword(), isMailingSubscription());
  }

  @Override
  public String toString() {
    return "RegistrationForm{" +
        "email='" + email + '\'' +
        ", name='" + name + '\'' +
        ", surname='" + surname + '\'' +
        ", nickname='" + nickname + '\'' +
        ", password='" + password + '\'' +
        ", repeatPassword='" + repeatPassword + '\'' +
        ", mailingSubscription=" + mailingSubscription +
        '}';
  }
}
