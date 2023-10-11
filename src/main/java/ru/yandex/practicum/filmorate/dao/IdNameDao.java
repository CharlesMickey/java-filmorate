package ru.yandex.practicum.filmorate.dao;

import java.util.List;
import java.util.Optional;

public interface IdNameDao<T> {
  List<T> getListItems();

  Optional<T> findItemById(Integer id);
}
