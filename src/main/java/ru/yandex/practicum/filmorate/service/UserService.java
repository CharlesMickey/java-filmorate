package ru.yandex.practicum.filmorate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendsDao;
import ru.yandex.practicum.filmorate.dao.StorageDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

@Service
public class UserService {

  private final StorageDao<User> storageDao;
  private final FriendsDao friendsDao;

  @Autowired
  public UserService(StorageDao<User> storageDao, FriendsDao friendsDao) {
    this.storageDao = storageDao;
    this.friendsDao = friendsDao;
  }

  public List<User> getListUsers() {
    return storageDao.getListItems();
  }

  public User createUser(User user) {
    if (user.getName() == null || user.getName().isBlank()) {
      user.setName(user.getLogin());
    }

    return storageDao.createItem(user);
  }

  public User updateUser(User user) {
    storageDao
      .findItemById(user.getId())
      .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    return storageDao.updateItem(user);
  }

  public User getUserById(Integer id) {
    return storageDao
      .findItemById(id)
      .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
  }

  public List<User> getAllFriends(Integer id) {
    storageDao
      .findItemById(id)
      .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

    List<Integer> friendsIds = friendsDao.getAllFriends(id);

    List<User> listFriends = new ArrayList<>();

    for (Integer friendId : friendsIds) {
      Optional<User> friend = storageDao.findItemById(friendId);
      friend.ifPresent(listFriends::add);
    }

    return listFriends;
  }

  public User addFriend(Integer userId, Integer friendId) {
    User user = storageDao
      .findItemById(userId)
      .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

    storageDao
      .findItemById(friendId)
      .orElseThrow(() -> new NotFoundException("Друг не найден"));

    if (userId == friendId) {
      throw new NotFoundException(
        "Одиночиство не порок. Так что не добавляйте себя к себе в друзья"
      );
    }

    friendsDao.addFriend(userId, friendId);

    return user;
  }

  public void deleteFriend(Integer userId, Integer friendId) {
    storageDao
      .findItemById(userId)
      .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

    storageDao
      .findItemById(friendId)
      .orElseThrow(() -> new NotFoundException("Друг не найден"));

    friendsDao.deleteFriend(userId, friendId);
  }

  public List<User> getCommonFriends(Integer userId, Integer otherId) {
    storageDao
      .findItemById(userId)
      .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

    storageDao
      .findItemById(otherId)
      .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

    List<User> userFriends = getAllFriends(userId);
    List<User> otherFriend = getAllFriends(otherId);

    List<User> listUsers = userFriends
      .stream()
      .filter(friend -> otherFriend.contains(friend))
      .collect(Collectors.toList());

    return listUsers;
  }
}
