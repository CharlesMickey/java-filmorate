package ru.yandex.practicum.filmorate.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class Rating implements Serializable {

  private final Integer id;

  private final String name;
}
