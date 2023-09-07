package ru.yandex.practicum.filmorate.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
    BindingResult bindingResult = ex.getBindingResult();
    Map<String, String> errors = new HashMap<>();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      errors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }

    errors.forEach((name, message) -> {
      log.error("Ошибка {}: {}", name, message);
    });

    return ResponseEntity.badRequest().body(errors);
  }
}
