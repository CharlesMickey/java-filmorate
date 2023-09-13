package ru.yandex.practicum.filmorate.service;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.JsonTransformer;

@Service
public class UserService {
  private int id = 0;
  private final HashMap<Integer, User> users = new HashMap<>();

  private int setId() {
    id++;
    return id;
  }

  public List<User> getUsers() {
    List<User> usersList = new ArrayList<>(users.values());
    return usersList;
  }

  public User createUser(User user) {
    if (
      user.getName() == null ||
      user.getName().isBlank()
    ) {
      user.setName(user.getLogin());
    }
    user.setId(setId());
    users.put(user.getId(), user);
    return user;
  }

  public User updateUser(User user) {
    if (users.get(user.getId()) == null) {
      throw new NotFoundException("Пользователь не найден");
    }

    users.put(user.getId(), user);
    return user;
  }
}
