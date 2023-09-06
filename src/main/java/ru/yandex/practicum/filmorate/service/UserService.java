package ru.yandex.practicum.filmorate.service;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.JsonTransformer;

@Service
public class UserService {

  private static Gson jsonTransformer = JsonTransformer.getGson();

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

  public ResponseEntity<String> createUser(User user) {
    if (
      user.getName() == null ||
      user.getName().isEmpty() ||
      user.getName().isBlank()
    ) {
      user.setName(user.getLogin());
    }
    user.setId(setId());
    users.put(user.getId(), user);
    return ResponseEntity.ok(jsonTransformer.toJson(user));
  }

  public ResponseEntity<String> updateUser(User user) {
    if (users.get(user.getId()) == null) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(jsonTransformer.toJson("Пользователь не найден"));
    }

    users.put(user.getId(), user);
    return ResponseEntity.ok(jsonTransformer.toJson(user));
  }
}
