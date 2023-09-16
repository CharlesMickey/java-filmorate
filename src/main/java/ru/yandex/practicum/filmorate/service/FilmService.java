package ru.yandex.practicum.filmorate.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

@Service
public class FilmService {

  private final FilmStorage filmStorage;
  private final UserStorage userStorage;

  @Autowired
  public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
    this.filmStorage = filmStorage;
    this.userStorage = userStorage;
  }

  public List<Film> getFilms() {
    return filmStorage.getListFilms();
  }

  public Film createFilm(Film film) {
    if (
      film.getReleaseDate() != null &&
      film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))
    ) {
      throw new BadRequestException(
        "ReleaseDate не может быть раньше 1895-12-28"
      );
    }

    return filmStorage.createFilm(film);
  }

  public Film updateFilm(Film film) {
    if (filmStorage.getFilms().get(film.getId()) == null) {
      throw new NotFoundException("Фильм не найден");
    }

    return filmStorage.updateFilm(film);
  }

  public Film getFilmById(Integer id) {
    if (filmStorage.getFilms().get(id) == null) {
      throw new NotFoundException("Фильм не найден");
    }

    return filmStorage.getFilms().get(id);
  }

  public Film addLike(Integer id, Integer userId) {
    Film film = filmStorage.getFilms().get(id);
    User user = userStorage.getUsers().get(userId);
    if (user == null) {
      throw new NotFoundException("Пользователь не найден");
    }

    if (film == null) {
      throw new NotFoundException("Фильм не найден");
    }

    film.getLikes().add(userId);

    return film;
  }

  public Film deleteLike(Integer id, Integer userId) {
    Film film = filmStorage.getFilms().get(id);
    User user = userStorage.getUsers().get(userId);
    if (user == null) {
      throw new NotFoundException("Пользователь не найден");
    }

    if (film == null) {
      throw new NotFoundException("Фильм не найден");
    }

    film.getLikes().remove(userId);

    return film;
  }

  public List<Film> getPopularFilms(Integer count) {
    return filmStorage
      .getListFilms()
      .stream()
      .sorted(Comparator.comparingInt(film -> -film.getLikes().size()))
      .limit(count)
      .collect(Collectors.toList());
  }
}
