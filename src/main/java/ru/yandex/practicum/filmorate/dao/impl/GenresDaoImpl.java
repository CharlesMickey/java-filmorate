package ru.yandex.practicum.filmorate.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenresDao;

@Component
public class GenresDaoImpl implements GenresDao {

  private final JdbcTemplate jdbcTemplate;

  public GenresDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Integer> getAllItems(Integer filmId) {
    List<Integer> listIds = new ArrayList<>();

    SqlRowSet sql = jdbcTemplate.queryForRowSet(
      "SELECT * FROM \"FilmGenre\" WHERE \"film_id\" = ? ORDER BY \"genre_id\" ASC",
      filmId
    );

    while (sql.next()) {
      Integer genreId = sql.getInt("genre_id");
      listIds.add(genreId);
    }

    return listIds;
  }

  @Override
  public void addItem(Integer filmId, Integer genreId) {
    String sqlQuery =
      "INSERT INTO \"FilmGenre\" (\"film_genre_id\",\"film_id\", \"genre_id\") " +
      "VALUES(?, ?, ?)";

    jdbcTemplate.update(sqlQuery, filmId + "_" + genreId, filmId, genreId);
  }

  @Override
  public void deleteItem(Integer filmId, Integer genreId) {
    String sqlDelete =
      "DELETE FROM \"FilmGenre\" WHERE( \"film_id\" = ? AND \"genre_id\" = ? )";
    jdbcTemplate.update(sqlDelete, filmId, genreId);
  }

  @Override
  public void deleteAllItem(Integer filmId) {
    String sqlDelete = "DELETE FROM \"FilmGenre\" WHERE( \"film_id\" = ? )";
    jdbcTemplate.update(sqlDelete, filmId);
  }
}
