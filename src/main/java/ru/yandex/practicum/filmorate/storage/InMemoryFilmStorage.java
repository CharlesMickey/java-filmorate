package ru.yandex.practicum.filmorate.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

@Component
public class InMemoryFilmStorage implements FilmStorage {

  private final HashMap<Integer, Film> films = new HashMap<>();
  private int id = 0;

  public int setId() {
    id++;
    return id;
  }

  public HashMap<Integer, Film> getFilms() {
    return films;
  }

  public List<Film> getListFilms() {
    List<Film> filmsList = new ArrayList<>(films.values());
    return filmsList;
  }

  public Film createFilm(Film film) {
    film.setId(setId());
    films.put(film.getId(), film);
    return film;
  }

  public Film updateFilm(Film film) {
    films.put(film.getId(), film);
    return film;
  }
}
