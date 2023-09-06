package ru.yandex.practicum.filmorate.service;

import com.google.gson.Gson;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.JsonTransformer;

@Service
public class FilmService {

  private static Gson jsonTransformer = JsonTransformer.getGson();
  private final HashMap<Integer, Film> films = new HashMap<>();
  private int id = 0;

  public int setId() {
    id++;
    return id;
  }

  public List<Film> getFilms() {
    List<Film> filmsList = new ArrayList<>(films.values());
    return filmsList;
  }

  public ResponseEntity<String> createFilm(Film film) {
    if (
      film.getReleaseDate() != null &&
      film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))
    ) {
      return ResponseEntity
        .badRequest()
        .body(
          jsonTransformer.toJson("ReleaseDate не может быть раньше 1895-12-28")
        );
    }
    film.setId(setId());
    films.put(film.getId(), film);
    return ResponseEntity.ok(jsonTransformer.toJson(film));
  }

  public ResponseEntity<String> updateFilm(Film film) {
    if (films.get(film.getId()) == null) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(jsonTransformer.toJson("Фильм не найден"));
    }

    films.put(film.getId(), film);
    return ResponseEntity.ok(jsonTransformer.toJson(film));
  }
}
