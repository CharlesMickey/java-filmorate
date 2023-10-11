package ru.yandex.practicum.filmorate.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.google.gson.Gson;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.dao.impl.UserStorageDaoImpl;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.JsonTransformer;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserTest {

  private final UserStorageDaoImpl userStorage;
  private User user;

  @BeforeEach
  void setUp() {
    user = new User();
    user.setEmail("user@example.com");
    user.setLogin("validLogin");
    user.setBirthday(LocalDate.of(1992, 1, 1));
    userStorage.createItem(user);
  }

  @Test
  public void testGetListItems() {
    List<User> userOptional = userStorage.getListItems();

    assertThat(userOptional).as("Список пользователей пуст").isNotEmpty();
  }

  @Test
  public void testFindUserById() {
    Optional<User> userOptional = userStorage.findItemById(1);

    assertThat(userOptional)
      .isPresent()
      .hasValueSatisfying(user ->
        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
      );
  }

  @Test
  void testCreateItem() {
    User newUser = new User();
    newUser.setEmail("newUserd@ya.ru");
    newUser.setLogin("Новыйлогин");
    newUser.setName("Новое Имя");
    newUser.setBirthday(LocalDate.of(1990, 1, 1));

    User createdUser = userStorage.createItem(newUser);

    assertThat(createdUser).isNotNull();
    assertThat(createdUser.getId()).isGreaterThan(0);
    assertThat(createdUser.getEmail()).isEqualTo("newUserd@ya.ru");
  }

  @Test
  void testUpdateItem() {
    User userToUpdate = new User();
    userToUpdate.setId(1);
    userToUpdate.setEmail("updated@ya.ru");
    userToUpdate.setLogin("Новое логин");
    userToUpdate.setName("Новое имя");
    userToUpdate.setBirthday(LocalDate.of(1980, 1, 1));

    User updatedUser = userStorage.updateItem(userToUpdate);

    assertThat(updatedUser).isNotNull();
    assertThat(updatedUser.getId()).isEqualTo(1);
    assertThat(updatedUser.getEmail()).isEqualTo("updated@ya.ru");
  }
}
