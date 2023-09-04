package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.JsonTransformer;

@RestController
@Slf4j
public class UserController {

  private static Gson jsonTransformer = JsonTransformer.getGson();

  private int id = 0;
  private final HashMap<Integer, User> users = new HashMap<>();

  private int setId() {
    id++;
    return id;
  }

  @GetMapping("/users")
  public List<User> getUsers() {
    List<User> usersList = new ArrayList<>(users.values());
    return usersList;
  }

  @PostMapping("/users")
  public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
    log.debug("Post запрос /users, передан: {}", user);
    if (user.getName() == null || user.getName().isEmpty()) {
      user.setName(user.getLogin());
    }
    user.setId(setId());
    users.put(user.getId(), user);
    return ResponseEntity.ok(jsonTransformer.toJson(user));
  }

  @PutMapping("/users")
  public ResponseEntity<String> updateUser(@RequestBody User user) {
    log.debug("Put запрос /users, передан: {}", user);

    if (users.get(user.getId()) == null) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(jsonTransformer.toJson("Пользователь не найден, сорри;)"));
    }

    users.put(user.getId(), user);
    return ResponseEntity.ok(jsonTransformer.toJson(user));
  }
}
