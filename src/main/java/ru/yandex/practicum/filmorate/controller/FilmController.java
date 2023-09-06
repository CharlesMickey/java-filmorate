package ru.yandex.practicum.filmorate.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    log.debug("Get запрос /films, передан: {}", filmsList);

    return filmsList;
  }

  @PostMapping("/films")
  public ResponseEntity<String> createFilm(@Valid @RequestBody Film film) {
    log.debug("Post запрос /films, передан: {}", film);
    return filmService.createFilm(film);
  }

  @PutMapping("/films")
  public ResponseEntity<String> updateFilm(@Valid @RequestBody Film film) {
    log.debug("Put запрос /films, передан: {}", film);
    return filmService.updateFilm(film);
  }
}
