package ru.yandex.practicum.filmorate.dao;

import java.util.List;

public interface LikesDao {
  List<Integer> getAllLikes(Integer filmId);

  void addLike(Integer userId, Integer filmId);

  void deleteLike(Integer userId, Integer filmId);
}
