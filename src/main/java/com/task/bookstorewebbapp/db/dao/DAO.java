package com.task.bookstorewebbapp.db.dao;

import com.task.bookstorewebbapp.db.SearchField;
import com.task.bookstorewebbapp.db.exception.DAOException;
import java.util.List;

public interface DAO<T> {

  <V> T getEntityByField(SearchField<V> fieldValue) throws DAOException;

  <V> List<T> getEntitiesByField(V fieldValue) throws DAOException;

  List<T> getEntities() throws DAOException;

  long insertEntity(T entity) throws DAOException;

  boolean updateEntity(T entity) throws DAOException;

  boolean deleteEntity(T entity) throws DAOException;

}
