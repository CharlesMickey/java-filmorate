package ru.yandex.practicum.filmorate.storage;

import java.util.HashMap;
import java.util.List;

public interface Storage<T> {
  int setId();

  HashMap<Integer, T> getItems();

  List<T> getListItems();

  T createItem(T item);

  T updateItem(T item);
}
