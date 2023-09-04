package ru.yandex.practicum.filmorate.user;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.google.gson.Gson;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.JsonTransformer;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

  private static Gson jsonTransformer = JsonTransformer.getGson();

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testValidUser() throws Exception {
    User user = new User();
    user.setEmail("user@example.com");
    user.setLogin("validLogin");
    user.setBirthday(LocalDate.of(1992, 1, 1));

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/users")
          .contentType("application/json")
          .content(jsonTransformer.toJson(user))
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andDo(print());
  }

  @Test
  public void testIsEmptyRequest() throws Exception {
    mockMvc
      .perform(
        MockMvcRequestBuilders.post("/users").contentType("application/json")
      )
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andDo(print());
  }

  @Test
  public void testNoValidUserEmail() throws Exception {
    User user = new User();
    user.setEmail("userexample.com");
    user.setLogin("validLogin");
    user.setBirthday(LocalDate.of(1992, 1, 1));

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/users")
          .contentType("application/json")
          .content(jsonTransformer.toJson(user))
      )
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(
        MockMvcResultMatchers
          .jsonPath("$.email")
          .value("Не верный формат email")
      )
      .andDo(print());
  }

  @Test
  public void testNoValidUserBirthday() throws Exception {
    User user = new User();
    user.setEmail("user@example.com");
    user.setLogin("validLogin");
    user.setBirthday(LocalDate.of(2992, 1, 1));

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/users")
          .contentType("application/json")
          .content(jsonTransformer.toJson(user))
      )
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(
        MockMvcResultMatchers
          .jsonPath("$.birthday")
          .value("Дата рождения не может быть в будущем")
      )
      .andDo(print());
  }

  @Test
  public void testNoValidUseLogin() throws Exception {
    User user = new User();
    user.setEmail("user@example.com");
    user.setLogin("no valid Login");
    user.setBirthday(LocalDate.of(1992, 1, 1));

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/users")
          .contentType("application/json")
          .content(jsonTransformer.toJson(user))
      )
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(
        MockMvcResultMatchers
          .jsonPath("$.login")
          .value("В логине не должно быть пробелов")
      )
      .andDo(print());
  }
}
