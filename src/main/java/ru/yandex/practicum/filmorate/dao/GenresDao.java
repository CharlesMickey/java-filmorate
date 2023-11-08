package ru.yandex.practicum.filmorate.dao;

import java.util.List;

public interface GenresDao {
  List<Integer> getAllItems(Integer id);

  void addItem(Integer id, Integer otherId);

  void deleteItem(Integer id, Integer otherId);

  void deleteAllItem(Integer filmId);
}
