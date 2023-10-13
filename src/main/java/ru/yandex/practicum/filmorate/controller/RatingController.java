package ru.yandex.practicum.filmorate.controller;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.RatingService;

@RestController
@Slf4j
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/mpa")
    public List<Rating> getListRatings() {
        List<Rating> ratingsList = ratingService.getListRatings();
        log.debug("Get request /genres , data transmitted: {}", ratingsList);

        return ratingsList;
    }

    @GetMapping("/mpa/{id}")
    public Rating findRatingById(@PathVariable Integer id) {
        log.debug("Get request /mpa/{id}", id);
        return ratingService.findRatingById(id);
    }
}
