package ru.yandex.practicum.filmorate.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.StorageDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

@Service
public class UserService {

  private final Storage<User> userStorage;

  private final StorageDao<User> storageDao;

  @Autowired
  public UserService(
    @Qualifier("inMemoryUserStorage") Storage<User> userStorage,
    StorageDao<User> storageDao
  ) {
    this.userStorage = userStorage;
    this.storageDao = storageDao;
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
    User user = userStorage.getItems().get(id);
    if (user == null) {
      throw new NotFoundException("Пользователь не найден");
    }

    return user
      .getFriends()
      .stream()
      .map(i -> userStorage.getItems().get(i))
      .collect(Collectors.toList());
  }

  public User addFriend(Integer id, Integer friendId) {
    User user = userStorage.getItems().get(id);
    User friend = userStorage.getItems().get(friendId);
    if (user == null) {
      throw new NotFoundException("Пользователь не найден");
    }

    if (friend == null) {
      throw new NotFoundException("Друг не найден");
    }

    if (id == friendId) {
      throw new NotFoundException(
        "Одиночиство не порок. Так что не добавляйте себя к себе в друзья"
      );
    }

    user.getFriends().add(friendId);
    friend.getFriends().add(id);

    return user;
  }

  public User deleteFriend(Integer id, Integer friendId) {
    User user = userStorage.getItems().get(id);
    User friend = userStorage.getItems().get(friendId);
    if (user == null) {
      throw new NotFoundException("Пользователь не найден");
    }

    if (friend == null) {
      throw new NotFoundException("Друг не найден");
    }

    user.getFriends().remove(friendId);
    friend.getFriends().remove(id);

    return user;
  }

  public List<User> getCommonFriends(Integer id, Integer otherId) {
    User user = userStorage.getItems().get(id);
    User otherUser = userStorage.getItems().get(otherId);

    if (user == null) {
      throw new NotFoundException("Пользователь не найден");
    }

    if (otherUser == null) {
      throw new NotFoundException("Друг не найден");
    }

    List<User> listNames = user
      .getFriends()
      .stream()
      .filter(friend -> otherUser.getFriends().contains(friend))
      .map(i -> userStorage.getItems().get(i))
      .collect(Collectors.toList());

    return listNames;
  }
}
