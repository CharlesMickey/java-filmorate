package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class User {

  private int id;

  @NotBlank(message = "Email обязательно поле")
  @Email(message = "Не верный формат email")
  private String email;

  @NotBlank(message = "Логин - обязательное поле")
  @Pattern(regexp = "\\S+", message = "В логине не должно быть пробелов")
  private String login;

  private String name;

  @PastOrPresent(message = "Дата рождения не может быть в будущем")
  private LocalDate birthday;

  private Set<Integer> friends = new HashSet<>();

}
