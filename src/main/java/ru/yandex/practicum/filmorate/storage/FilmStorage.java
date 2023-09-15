package ru.yandex.practicum.filmorate.storage;

import java.util.HashMap;
import java.util.List;
import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {
  int setId();

  HashMap<Integer, Film> getFilms();

  List<Film> getListFilms();

  Film createFilm(Film film);

  Film updateFilm(Film film);
}
