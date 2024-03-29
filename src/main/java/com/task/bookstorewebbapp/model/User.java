package com.task.bookstorewebbapp.model;

import com.task.bookstorewebbapp.db.entity.UserEntity;
import java.util.Objects;

public class User {

  private long id;
  private String email;
  private String name;
  private String surname;
  private String nickname;
  private boolean mailingSubscription;

  private String avatarSource;

  public User() {
  }

  public static User toModel(UserEntity entity) {
    User user = new User();
    user.setId(entity.getId());
    user.setNickname(entity.getNickname());
    user.setName(entity.getName());
    user.setSurname(entity.getSurname());
    user.setEmail(entity.getEmail());
    user.setMailingSubscription(entity.isMailingSubscription());
    return user;
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

  public boolean isMailingSubscription() {
    return mailingSubscription;
  }

  public void setMailingSubscription(boolean mailingSubscription) {
    this.mailingSubscription = mailingSubscription;
  }

  public String getAvatarSource() {
    return avatarSource;
  }

  public void setAvatarSource(String avatarSource) {
    this.avatarSource = avatarSource;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return getId() == user.getId() && isMailingSubscription() == user.isMailingSubscription()
        && getEmail().equals(user.getEmail()) && getName().equals(user.getName())
        && getSurname().equals(user.getSurname()) && getNickname().equals(user.getNickname());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getEmail(), getName(), getSurname(), getNickname(),
        isMailingSubscription());
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", name='" + name + '\'' +
        ", surname='" + surname + '\'' +
        ", nickName='" + nickname + '\'' +
        ", mailingSubscription=" + mailingSubscription +
        '}';
  }


}
