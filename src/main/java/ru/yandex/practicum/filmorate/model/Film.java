package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class Film {

  private int id;

  @NotBlank(message = "Название не может быть пустым")
  private  String name;

  @Size(max = 200, message = "Максимальная длина описания — 200 символов")
  private  String description;

  private  LocalDate releaseDate;

  @Positive(message = "Продолжительность фильма должна быть положительной")
  private  int duration;
}
