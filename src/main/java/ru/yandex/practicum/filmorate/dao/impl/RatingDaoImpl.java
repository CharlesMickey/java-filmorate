package ru.yandex.practicum.filmorate.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.IdNameDao;
import ru.yandex.practicum.filmorate.model.Rating;

@Component
public class RatingDaoImpl implements IdNameDao<Rating> {

  JdbcTemplate jdbcTemplate;

  public RatingDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Rating> getListItems() {
    String sql = "SELECT * FROM \"MPAARating\"";

    return jdbcTemplate.query(sql, (rs, rowNum) -> makeRating(rs));
  }

  @Override
  public Optional<Rating> findItemById(Integer id) {
    SqlRowSet srs = jdbcTemplate.queryForRowSet(
      "select * from \"MPAARating\" where \"rating_id\" = ?",
      id
    );

    if (srs.next()) {
      Rating rating = new Rating(
        srs.getInt("rating_id"),
        srs.getString("rating_name")
      );

      return Optional.of(rating);
    } else {
      return Optional.empty();
    }
  }

  private Rating makeRating(ResultSet rs) throws SQLException {
    Integer id = rs.getInt("rating_id");
    String name = rs.getString("rating_name");

    Rating rating = new Rating(id, name);

    return rating;
  }
}
