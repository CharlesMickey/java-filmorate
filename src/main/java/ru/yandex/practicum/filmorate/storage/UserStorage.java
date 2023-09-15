package ru.yandex.practicum.filmorate.storage;

import java.util.HashMap;
import java.util.List;
import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {
  int setId();

  HashMap<Integer, User> getUsers();

  List<User> getListUsers();

  User createUser(User user);

  User updateUser(User user);
}
