package ru.yandex.practicum.filmorate.controller;

import java.time.LocalDate;
import java.util.HashMap;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

@RestController
@Slf4j
public class FilmController {

  private int id = 0;
  private final HashMap<Integer, Film> films = new HashMap<>();

  public int setId() {
    id++;
    return id;
  }

  @GetMapping("/films")
  public HashMap<Integer, Film> getFilms() {
    return films;
  }

  @PostMapping("/films")
  public ResponseEntity<String> createFilm(@Valid @RequestBody Film film) {
    log.debug("Post запрос /users, передан: {}", film);

    if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
      return ResponseEntity
        .badRequest()
        .body("Дата релиза — должна быть не раньше 28 декабря 1895 года;");
    }
    film.setId(setId());
    films.put(film.getId(), film);
    return ResponseEntity.ok("Фильм успешно добавлен.");
  }

  @PutMapping("/films")
  public ResponseEntity<String> updateFilm(@Valid @RequestBody Film film) {
    log.debug("Put запрос /users, передан: {}", film);

    if (films.get(film.getId()) == null) {
      film.setId(setId());
      films.put(film.getId(), film);
      return ResponseEntity.ok(
        "Нет фильма с указанным id. Фильм создан с новым id: " + film.getId()
      );
    }

    films.put(film.getId(), film);
    return ResponseEntity.ok("Данные фильма успешно обновлены.");
  }
}
