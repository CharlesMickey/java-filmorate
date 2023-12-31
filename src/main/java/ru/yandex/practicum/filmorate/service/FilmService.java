package ru.yandex.practicum.filmorate.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.LikesDao;
import ru.yandex.practicum.filmorate.dao.StorageDao;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

@Service
public class FilmService {

  private final StorageDao<Film> filmStorageDao;
  private final StorageDao<User> userStorageDao;

  private final LikesDao likesDao;

  @Autowired
  public FilmService(
    StorageDao<Film> filmStorageDao,
    StorageDao<User> userStorageDao,
    LikesDao likesDao
  ) {
    this.filmStorageDao = filmStorageDao;
    this.userStorageDao = userStorageDao;
    this.likesDao = likesDao;
  }

  public List<Film> getListFilms() {
    return filmStorageDao.getListItems();
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

    return filmStorageDao.createItem(film);
  }

  public Film updateFilm(Film film) {
    filmStorageDao
      .findItemById(film.getId())
      .orElseThrow(() -> new NotFoundException("Фильм не найден"));

    return filmStorageDao.updateItem(film);
  }

  public Film getFilmById(Integer id) {
    return filmStorageDao
      .findItemById(id)
      .orElseThrow(() -> new NotFoundException("Фильм не найден"));
  }

  public Film addLike(Integer filmId, Integer userId) {
    Film film = filmStorageDao
      .findItemById(filmId)
      .orElseThrow(() -> new NotFoundException("Фильм не найден"));

    userStorageDao
      .findItemById(userId)
      .orElseThrow(() -> new NotFoundException("Поьзователь не найден"));

    likesDao.addLike(userId, filmId);

    return film;
  }

  public Film deleteLike(Integer filmId, Integer userId) {
    Film film = filmStorageDao
      .findItemById(filmId)
      .orElseThrow(() -> new NotFoundException("Фильм не найден"));

    userStorageDao
      .findItemById(userId)
      .orElseThrow(() -> new NotFoundException("Поьзователь не найден"));

    likesDao.deleteLike(userId, filmId);

    return film;
  }

  public List<Film> getPopularFilms(Integer count) {
    return filmStorageDao
      .getListItems()
      .stream()
      .sorted(Comparator.comparingInt(film -> -film.getLikes().size()))
      .limit(count)
      .collect(Collectors.toList());
  }
}
