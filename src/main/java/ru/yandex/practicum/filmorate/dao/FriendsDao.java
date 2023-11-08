package ru.yandex.practicum.filmorate.dao;

import java.util.List;

public interface FriendsDao {
  List<Integer> getAllFriends(Integer userId);

  void addFriend(Integer userId, Integer friendId);

  void deleteFriend(Integer userId, Integer friendId);
}
