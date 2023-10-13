package ru.yandex.practicum.filmorate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.IdNameDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

@Service
public class GenreService {

  private final IdNameDao<Genre> storageGenreDao;

  public GenreService(IdNameDao<Genre> storageGenreDao) {
    this.storageGenreDao = storageGenreDao;
  }

  public List<Genre> getListGenres() {
    return storageGenreDao.getListItems();
  }

  public Genre findGenreById(Integer id) {
    return storageGenreDao
      .findItemById(id)
      .orElseThrow(() -> new NotFoundException("Жанр не найден"));
  }
}
