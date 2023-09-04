package ru.yandex.practicum.filmorate.controller;

import java.util.HashMap;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

@RestController
@Slf4j
public class UserController {

  private int id = 0;
  private final HashMap<Integer, User> users = new HashMap<>();

  private int setId() {
    id++;
    return id;
  }

  @GetMapping("/users")
  public HashMap<Integer, User> getUsers() {
    return users;
  }

  @PostMapping("/users")
  public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
    log.debug("Post запрос /users, передан: {}", user);
    if(user.getName() == null || user.getName().isEmpty() ) {
      user.setName(user.getLogin());
    }
    user.setId(setId());
    users.put(user.getId(), user);
    return ResponseEntity.ok("Пользователь успешно добавлен.");
  }

  @PutMapping("/users")
  public ResponseEntity<String> updateUser(@RequestBody User user) {
    log.debug("Put запрос /users, передан: {}", user);

    if (users.get(user.getId()) == null) {
      user.setId(setId());
      users.put(user.getId(), user);
      return ResponseEntity.ok(
        "Нет пользователя с указанным id. Пользователь создан с новым id: " +
        user.getId()
      );
    }

    users.put(user.getId(), user);
    return ResponseEntity.ok("Данные пользователя успешно обновлены.");
  }
}
