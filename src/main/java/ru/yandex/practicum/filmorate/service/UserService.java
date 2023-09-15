package ru.yandex.practicum.filmorate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

@Service
public class UserService {

  private final UserStorage userStorage;

  @Autowired
  public UserService(UserStorage userStorage) {
    this.userStorage = userStorage;
  }

  public List<User> getUsers() {
    return userStorage.getListUsers();
  }

  public User createUser(User user) {
    if (user.getName() == null || user.getName().isBlank()) {
      user.setName(user.getLogin());
    }

    return userStorage.createUser(user);
  }

  public User updateUser(User user) {
    if (userStorage.getUsers().get(user.getId()) == null) {
      throw new NotFoundException("Пользователь не найден");
    }

    return userStorage.updateUser(user);
  }

  public Set<Integer> getAllFriends(Integer id) {
    User user = userStorage.getUsers().get(id);
    if (user == null) {
      throw new NotFoundException("Пользователь не найден");
    }

    return user.getFriends();
  }

  public User addFriend(Integer id, Integer friendId) {
    User user = userStorage.getUsers().get(id);
    User friend = userStorage.getUsers().get(friendId);
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
    User user = userStorage.getUsers().get(id);
    User friend = userStorage.getUsers().get(friendId);
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

  public List<String> getCommonFriends(Integer id, Integer otherId) {
    User user = userStorage.getUsers().get(id);
    User otherUser = userStorage.getUsers().get(otherId);

    if (user == null) {
      throw new NotFoundException("Пользователь не найден");
    }

    if (otherUser == null) {
      throw new NotFoundException("Друг не найден");
    }

    List<String> listNames = user
      .getFriends()
      .stream()
      .filter(friend -> otherUser.getFriends().contains(friend))
      .map(i -> userStorage.getUsers().get(i).getName())
      .collect(Collectors.toList());

    return listNames;
  }
}
