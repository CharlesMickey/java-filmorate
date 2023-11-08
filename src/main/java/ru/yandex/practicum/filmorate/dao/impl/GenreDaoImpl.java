package ru.yandex.practicum.filmorate.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.IdNameDao;
import ru.yandex.practicum.filmorate.model.Genre;

@Component
public class GenreDaoImpl implements IdNameDao<Genre> {

  private final JdbcTemplate jdbcTemplate;

  public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Genre> getListItems() {
    String sql = "SELECT * FROM \"Genre\"";

    return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
  }

  @Override
  public Optional<Genre> findItemById(Integer id) {
    SqlRowSet srs = jdbcTemplate.queryForRowSet(
      "select * from \"Genre\" where \"genre_id\" = ? ORDER BY \"genre_id\" ASC",
      id
    );

    if (srs.next()) {
      Genre genre = new Genre(
        srs.getInt("genre_id"),
        srs.getString("genre_name")
      );

      return Optional.of(genre);
    } else {
      return Optional.empty();
    }
  }

  private Genre makeGenre(ResultSet rs) throws SQLException {
    Integer id = rs.getInt("genre_id");
    String name = rs.getString("genre_name");

    Genre genre = new Genre(id, name);

    return genre;
  }
}
