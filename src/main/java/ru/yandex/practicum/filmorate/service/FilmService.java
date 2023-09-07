package ru.yandex.practicum.filmorate.service;

import com.google.gson.Gson;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
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

  public String createFilm(Film film) {
    if (
      film.getReleaseDate() != null &&
      film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))
    ) {
      throw new BadRequestException(
        "ReleaseDate не может быть раньше 1895-12-28"
      );
    }
    film.setId(setId());
    films.put(film.getId(), film);
    return jsonTransformer.toJson(film);
  }

  public String updateFilm(Film film) {
    if (films.get(film.getId()) == null) {
      throw new NotFoundException("Фильм не найден");
    }

    films.put(film.getId(), film);
    return jsonTransformer.toJson(film);
  }
}
