package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.JsonTransformer;

@RestController
@Slf4j
public class FilmController {

  private static Gson jsonTransformer = JsonTransformer.getGson();

  private int id = 0;
  private final HashMap<Integer, Film> films = new HashMap<>();

  public int setId() {
    id++;
    return id;
  }

  @GetMapping("/films")
  public List<Film> getFilms() {
    List<Film> filmsList = new ArrayList<>(films.values());
    return filmsList;
  }

  @PostMapping("/films")
  public ResponseEntity<String> createFilm(@Valid @RequestBody Film film) {
    log.debug("Post запрос /users, передан: {}", film);

    if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
      return ResponseEntity.badRequest().body(jsonTransformer.toJson(film));
    }
    film.setId(setId());
    films.put(film.getId(), film);
    return ResponseEntity.ok(jsonTransformer.toJson(film));
  }

  @PutMapping("/films")
  public ResponseEntity<String> updateFilm(@Valid @RequestBody Film film) {
    log.debug("Put запрос /users, передан: {}", film);

    if (films.get(film.getId()) == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(jsonTransformer.toJson("Фильм не найден, соррян:)"));

    }

    films.put(film.getId(), film);
    return ResponseEntity.ok(jsonTransformer.toJson(film));
  }
}
