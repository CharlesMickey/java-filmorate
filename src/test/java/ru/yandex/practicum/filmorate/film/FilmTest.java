package ru.yandex.practicum.filmorate.film;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.google.gson.Gson;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.JsonTransformer;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmTest {

  private Film film;
  private static Gson jsonTransformer = JsonTransformer.getGson();

  @BeforeEach
  void setUp() {
    film = new Film();
    film.setName("Жизнь прекрасна");
    film.setDescription(
      "Времена Холокоста. Отец создает для сына фантастическую реальность, защищая его от ужасов войны." +
      "Эта история о любви и выживании."
    );
    film.setReleaseDate(LocalDate.of(1997, 1, 1));
    film.setDuration(1);
  }

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testValidUser() throws Exception {
    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/films")
          .contentType("application/json")
          .content(jsonTransformer.toJson(film))
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andDo(print());
  }

  @Test
  public void testIsEmptyRequest() throws Exception {
    mockMvc
      .perform(
        MockMvcRequestBuilders.post("/films").contentType("application/json")
      )
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andDo(print());
  }

  @Test
  public void testNoValidDescription() throws Exception {
    film.setDescription(
      "Фильм \"Жизнь прекрасна\" (La vita è bella) - итальянская драмеди, выпущенная в 1997 году, режиссированная и" +
      " снятая Роберто Бениньи. Он рассказывает историю Гвидо, еврейского отца, и его маленького сына, Джошуа," +
      " которые отправляются в невероятное путешествие, чтобы защитить свою жизнь и сохранить свой оптимизм во " +
      "времена Холокоста."
    );
    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/films")
          .contentType("application/json")
          .content(jsonTransformer.toJson(film))
      )
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(
        MockMvcResultMatchers
          .jsonPath("$.description")
          .value("Максимальная длина описания — 200 символов")
      )
      .andDo(print());
  }

  @Test
  public void testNoValidFilmName() throws Exception {
    film.setName("");
    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/films")
          .contentType("application/json")
          .content(jsonTransformer.toJson(film))
      )
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(
        MockMvcResultMatchers
          .jsonPath("$.name")
          .value("Название не может быть пустым")
      )
      .andDo(print());
  }

  @Test
  public void testNoValidDuration() throws Exception {
    film.setDuration(-1);

    mockMvc
      .perform(
        MockMvcRequestBuilders
          .post("/films")
          .contentType("application/json")
          .content(jsonTransformer.toJson(film))
      )
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(
        MockMvcResultMatchers
          .jsonPath("$.duration")
          .value("Продолжительность фильма должна быть положительной")
      )
      .andDo(print());
  }
}
