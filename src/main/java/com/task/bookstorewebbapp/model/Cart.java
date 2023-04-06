package com.task.bookstorewebbapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Cart {
  private final Map<Long, Integer> idQuantityMap = new HashMap<>();

  public Cart(){}

  public void addToCart(long id, int quantity){
    idQuantityMap.merge(id, quantity, Integer::sum);
  }

  public boolean isEmpty(){
    return idQuantityMap.isEmpty();
  }

  public int getAmountById(long id){
    return idQuantityMap.get(id);
  }

  public Set<Entry<Long, Integer>> getEntries(){
    return idQuantityMap.entrySet();
  }

  public void setQuantity(long id, int quantity){
    idQuantityMap.put(id, quantity);
  }

  public void remove(long id){
    idQuantityMap.remove(id);
  }

  public void cleanCart() {
    idQuantityMap.clear();
  }
  public int getItemQuantity(){
   return idQuantityMap.values().stream().reduce(0, Integer::sum);
  }

}
