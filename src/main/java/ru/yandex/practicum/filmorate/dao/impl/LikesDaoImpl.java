package ru.yandex.practicum.filmorate.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.LikesDao;

@Component
public class LikesDaoImpl implements LikesDao {

  private final JdbcTemplate jdbcTemplate;

  public LikesDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Integer> getAllLikes(Integer filmId) {
    List<Integer> userIds = new ArrayList<>();

    SqlRowSet sql = jdbcTemplate.queryForRowSet(
      "SELECT * FROM \"FilmLikes\" WHERE \"film_id\" = ?",
      filmId
    );

    while (sql.next()) {
      Integer userId = sql.getInt("user_id");
      userIds.add(userId);
    }

    return userIds;
  }

  @Override
  public void addLike(Integer userId, Integer filmId) {
    String sqlQuery =
      "INSERT INTO \"FilmLikes\" (\"film_user_id\",\"film_id\", \"user_id\") " +
      "VALUES(?, ?, ?)";

    jdbcTemplate.update(sqlQuery, filmId + "_" + userId, filmId, userId);
  }

  @Override
  public void deleteLike(Integer userId, Integer filmId) {
    String sqlDelete =
      "DELETE FROM \"FilmLikes\" WHERE( \"film_id\" = ? AND \"user_id\" = ? )";
    jdbcTemplate.update(sqlDelete, filmId, userId);
  }
}
