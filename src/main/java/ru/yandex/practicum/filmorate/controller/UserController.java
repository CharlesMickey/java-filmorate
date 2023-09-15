package ru.yandex.practicum.filmorate.controller;

import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
  public User createUser(@Valid @RequestBody User user) {
    log.debug("Post equest /users, data transmitted: {}", user);
    return userService.createUser(user);
  }

  @PutMapping("/users")
  public User updateUser(@RequestBody User user) {
    log.debug("Put request /users, data transmitted: {}", user);
    return userService.updateUser(user);
  }

  @GetMapping("/user/{id}/friends")
  public Set<Integer> getUserFriends(@PathVariable Integer id) {
    log.debug("Get request /users/{}/friends", id);
    return userService.getAllFriends(id);
  }

  @PutMapping("/users/{id}/friends/{friendId}")
  public User addFriend(
    @PathVariable Integer id,
    @PathVariable Integer friendId
  ) {
    log.debug("Put request /users/{}/friends/{}", id, friendId);
    return userService.addFriend(id, friendId);
  }

  @DeleteMapping("/users/{id}/friends/{friendId}")
  public User deleteFriend(
    @PathVariable Integer id,
    @PathVariable Integer friendId
  ) {
    log.debug("Delete request /users/{}/friends/{}", id, friendId);
    return userService.deleteFriend(id, friendId);
  }

  @GetMapping("/users/{id}/friends/common/{otherId}")
  public List<String> getCommonFriends(
    @PathVariable Integer id,
    @PathVariable Integer otherId
  ) {
    log.debug("Get request /users/{}/friends/common/{}", id, otherId);
    return userService.getCommonFriends(id, otherId);
  }
}
