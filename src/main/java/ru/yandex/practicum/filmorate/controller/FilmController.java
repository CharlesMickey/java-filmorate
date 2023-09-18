package ru.yandex.practicum.filmorate.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

@RestController
@Slf4j
public class FilmController {

  private final FilmService filmService;

  @Autowired
  public FilmController(FilmService filmService) {
    this.filmService = filmService;
  }

  @GetMapping("/films")
  public List<Film> getFilms() {
    List<Film> filmsList = filmService.getListFilms();
    log.debug("Get request /films, data transmitted: {}", filmsList);

    return filmsList;
  }

  @PostMapping("/films")
  public Film createFilm(@Valid @RequestBody Film film) {
    log.debug("Post request /films, data transmitted: {}", film);
    return filmService.createFilm(film);
  }

  @PutMapping("/films")
  public Film updateFilm(@Valid @RequestBody Film film) {
    log.debug("Put request /films, data transmitted: {}", film);
    return filmService.updateFilm(film);
  }

  @GetMapping("/films/{id}")
  public Film getFilmsById(@PathVariable Integer id) {
    log.debug("Get request /films/{id}", id);
    return filmService.getFilmById(id);
  }

  @PutMapping("/films/{id}/like/{userId}")
  public Film addLike(@PathVariable Integer id, @PathVariable Integer userId) {
    log.debug("Put request /films/{}/like/{}", id, userId);
    return filmService.addLike(id, userId);
  }

  @DeleteMapping("/films/{id}/like/{userId}")
  public Film deleteLike(@PathVariable Integer id,
    @PathVariable Integer userId
  ) {
    log.debug("Delete like request /films/{}/like/{}", id, userId);
    return filmService.deleteLike(id, userId);
  }

  @GetMapping("/films/popular")
  public List<Film> getPopularFilms(
    @RequestParam(value = "count", defaultValue = "10") Integer count
  ) {
    if (count <= 0) {
      throw new BadRequestException(
        "Если не хотите указывать количество – просто не указывайте."
      );
    }
    return filmService.getPopularFilms(count);
  }
}
