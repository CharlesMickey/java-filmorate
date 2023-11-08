package ru.yandex.practicum.filmorate.film;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dao.impl.FilmStorageDaoImpl;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmTest {

  private final FilmStorageDaoImpl filmStorage;

  @BeforeEach
  void setUp() {
    Rating mpa = new Rating(1, "G");
    Film film = new Film();
    film.setName("Жизнь прекрасна");
    film.setDescription(
      "Времена Холокоста. Отец создает для сына фантастическую реальность, защищая его от ужасов войны." +
      "Эта история о любви и выживании."
    );
    film.setReleaseDate(LocalDate.of(1997, 1, 1));
    film.setDuration(1);
    film.setMpa(mpa);

    filmStorage.createItem(film);
  }

  @Test
  void testGetListItems() {
    List<Film> films = filmStorage.getListItems();
    assertThat(films).isNotEmpty();
  }

  @Test
  void testFindItemById() {
    Optional<Film> filmOptional = filmStorage.findItemById(1);
    assertThat(filmOptional).isPresent();

    Film film = filmOptional.get();
    assertThat(film.getId()).isEqualTo(1);
    assertThat(film.getName()).isEqualTo("Жизнь прекрасна");
  }

  @Test
  void testCreateItem() {
    Film newFilm = new Film();
    newFilm.setName("Новый фильм");
    newFilm.setDescription("Описание нового фильма");
    newFilm.setReleaseDate(LocalDate.now());
    newFilm.setDuration(120);
    newFilm.setMpa(new Rating(1, "PG"));

    Genre genre1 = new Genre(1, "Комедия");
    Genre genre2 = new Genre(2, "Боевик");
    Set<Genre> genres = Set.of(genre1, genre2);
    newFilm.setGenres(genres);

    Film createdFilm = filmStorage.createItem(newFilm);

    assertThat(createdFilm).isNotNull();
    assertThat(createdFilm.getId()).isGreaterThan(0);
    assertThat(createdFilm.getName()).isEqualTo("Новый фильм");
  }

  @Test
  void testUpdateItem() {
    Film filmToUpdate = new Film();
    filmToUpdate.setId(1);
    filmToUpdate.setName("Обновленное имя");
    filmToUpdate.setDescription("Обновленное описание");
    filmToUpdate.setReleaseDate(LocalDate.now());
    filmToUpdate.setDuration(150);
    filmToUpdate.setMpa(new Rating(2, "PG-13"));

    Genre genre1 = new Genre(1, "Жанр 1");
    Genre genre3 = new Genre(3, "Жанр 3");
    Set<Genre> genres = Set.of(genre1, genre3);
    filmToUpdate.setGenres(genres);

    Film updatedFilm = filmStorage.updateItem(filmToUpdate);

    assertThat(updatedFilm).isNotNull();
    assertThat(updatedFilm.getId()).isEqualTo(1);
    assertThat(updatedFilm.getName()).isEqualTo("Обновленное имя");
  }
}
