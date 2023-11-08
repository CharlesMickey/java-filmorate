package ru.yandex.practicum.filmorate.service;

import java.util.List;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.IdNameDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;

@Service
public class RatingService {

  private final IdNameDao<Rating> storageRatingDao;

  public RatingService(IdNameDao<Rating> storageRatingDao) {
    this.storageRatingDao = storageRatingDao;
  }

  public List<Rating> getListRatings() {
    return storageRatingDao.getListItems();
  }

  public Rating findRatingById(Integer id) {
    return storageRatingDao
      .findItemById(id)
      .orElseThrow(() -> new NotFoundException("Рейтинг не найден"));
  }
}
