package ru.yandex.practicum.filmorate.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

@Component
public class InMemoryUserStorage implements UserStorage {

  private int id = 0;
  private final HashMap<Integer, User> users = new HashMap<>();

  public int setId() {
    id++;
    return id;
  }

  public HashMap<Integer, User> getUsers() {
    return users;
  }

  public List<User> getListUsers() {
    List<User> usersList = new ArrayList<>(users.values());
    return usersList;
  }

  public User createUser(User user) {
    user.setId(setId());
    users.put(user.getId(), user);
    return user;
  }

  public User updateUser(User user) {
    users.put(user.getId(), user);
    return user;
  }
}
