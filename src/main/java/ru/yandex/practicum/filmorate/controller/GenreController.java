package ru.yandex.practicum.filmorate.controller;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

@RestController
@Slf4j
public class GenreController {

  private final GenreService genreService;

  public GenreController(GenreService genreService) {
    this.genreService = genreService;
  }

  @GetMapping("/genres")
  public List<Genre> getListGenres() {
    List<Genre> genreList = genreService.getListGenres();
    log.debug("Get request /genres , data transmitted: {}", genreList);

    return genreList;
  }

  @GetMapping("/genres/{id}")
  public Genre findGenreById(@PathVariable Integer id) {
    log.debug("Get request /genres/{id}", id);
    return genreService.findGenreById(id);
  }
}
