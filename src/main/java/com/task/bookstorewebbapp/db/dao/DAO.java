package com.task.bookstorewebbapp.db.dao;

import com.task.bookstorewebbapp.db.SearchField;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

  <V> T getEntityByField(SearchField<V> fieldValue) throws SQLException;

  <V> List<T> getEntitiesByField(V fieldValue) throws SQLException;

  List<T> getEntities() throws SQLException;

  long insertEntity(T entity) throws SQLException;

  boolean updateEntity(T entity) throws SQLException;

  boolean deleteEntity(T entity) throws SQLException;

}
