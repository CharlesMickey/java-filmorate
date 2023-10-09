package ru.yandex.practicum.filmorate.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.IdNameDao;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

@Service
public class FilmService {

  private final Storage<Film> filmStorage;

  private final Storage<User> userStorage;
  private final IdNameDao<Genre> storageGenreDao;
  private final IdNameDao<Rating> storageRatingDao;

  @Autowired
  public FilmService(
    @Qualifier("inMemoryFilmStorage") Storage<Film> filmStorage,
    @Qualifier("inMemoryUserStorage") Storage<User> userStorage,
    IdNameDao<Genre> storageGenreDao,
    IdNameDao<Rating> storageRatingDao
  ) {
    this.filmStorage = filmStorage;
    this.userStorage = userStorage;
    this.storageGenreDao = storageGenreDao;
    this.storageRatingDao = storageRatingDao;
  }

  public List<Genre> getListGenres() {
    return storageGenreDao.getListItems();
  }

  public Genre findGenreById(Integer id) {
    return storageGenreDao
      .findItemById(id)
      .orElseThrow(() -> new NotFoundException("Жанр не найден"));
  }

  public List<Rating> getListRatings() {
    return storageRatingDao.getListItems();
  }

  public Rating findRatingById(Integer id) {
    return storageRatingDao
      .findItemById(id)
      .orElseThrow(() -> new NotFoundException("Рейтинг не найден"));
  }

  public List<Film> getListFilms() {
    return filmStorage.getListItems();
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

    return filmStorage.createItem(film);
  }

  public Film updateFilm(Film film) {
    if (filmStorage.getItems().get(film.getId()) == null) {
      throw new NotFoundException("Фильм не найден");
    }

    return filmStorage.updateItem(film);
  }

  public Film getFilmById(Integer id) {
    if (filmStorage.getItems().get(id) == null) {
      throw new NotFoundException("Фильм не найден");
    }

    return filmStorage.getItems().get(id);
  }

  public Film addLike(Integer id, Integer userId) {
    Film film = filmStorage.getItems().get(id);
    User user = userStorage.getItems().get(userId);
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
    Film film = filmStorage.getItems().get(id);
    User user = userStorage.getItems().get(userId);
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
      .getListItems()
      .stream()
      .sorted(Comparator.comparingInt(film -> -film.getLikes().size()))
      .limit(count)
      .collect(Collectors.toList());
  }
}
