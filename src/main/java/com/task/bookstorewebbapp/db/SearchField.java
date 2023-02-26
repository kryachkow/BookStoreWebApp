package com.task.bookstorewebbapp.db;

import java.util.Objects;

public class SearchField<T> {

  private String name;
  private T value;

  public SearchField() {
  }

  public SearchField(String name, T value) {
    this.name = name;
    this.value = value;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SearchField)) {
      return false;
    }
    SearchField<?> that = (SearchField<?>) o;
    return Objects.equals(getName(), that.getName()) && Objects.equals(getValue(),
        that.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getValue());
  }

  @Override
  public String toString() {
    return "SearchColumn{" +
        "name='" + name + '\'' +
        ", value=" + value +
        '}';
  }
}
