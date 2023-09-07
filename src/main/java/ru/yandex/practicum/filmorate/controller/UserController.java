package ru.yandex.practicum.filmorate.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

@RestController
@Slf4j
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  public List<User> getUsers() {
    List<User> usersList = userService.getUsers();
    log.debug("Get request /users, data transmitted: {}", usersList);
    return usersList;
  }

  @PostMapping("/users")
  public String createUser(@Valid @RequestBody User user) {
    log.debug("Post equest /users, data transmitted: {}", user);
    return userService.createUser(user);
  }

  @PutMapping("/users")
  public String updateUser(@RequestBody User user) {
    log.debug("Put request /users, data transmitted: {}", user);
    return userService.updateUser(user);
  }
}
