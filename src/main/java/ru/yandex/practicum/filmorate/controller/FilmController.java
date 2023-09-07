package ru.yandex.practicum.filmorate.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    List<Film> filmsList = filmService.getFilms();
    log.debug("Get request /films, data transmitted: {}", filmsList);

    return filmsList;
  }

  @PostMapping("/films")
  public String createFilm(@Valid @RequestBody Film film) {
    log.debug("Post request /films, data transmitted: {}", film);
    return filmService.createFilm(film);
  }

  @PutMapping("/films")
  public String updateFilm(@Valid @RequestBody Film film) {
    log.debug("Put request /films, data transmitted: {}", film);
    return filmService.updateFilm(film);
  }
}
