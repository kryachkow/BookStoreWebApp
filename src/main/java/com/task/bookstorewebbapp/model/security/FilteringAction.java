package com.task.bookstorewebbapp.model.security;

import jakarta.servlet.ServletException;
import java.io.IOException;

@FunctionalInterface
public interface FilteringAction<V> {

  void acceptAggregator(FilteringAggregator<V> aggregator) throws IOException, ServletException;

}
