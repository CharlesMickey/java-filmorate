package ru.yandex.practicum.filmorate.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface StorageDao<T> {
  List<T> getListItems();

  Optional<T> findItemById(Integer id);

  T createItem(T item);

  T updateItem(T item);
}
