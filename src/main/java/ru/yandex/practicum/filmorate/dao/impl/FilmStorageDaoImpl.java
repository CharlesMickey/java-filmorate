package ru.yandex.practicum.filmorate.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenresDao;
import ru.yandex.practicum.filmorate.dao.IdNameDao;
import ru.yandex.practicum.filmorate.dao.LikesDao;
import ru.yandex.practicum.filmorate.dao.StorageDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

@Component
public class FilmStorageDaoImpl implements StorageDao<Film> {

  private final JdbcTemplate jdbcTemplate;

  private final GenresDao genresDao;
  private final IdNameDao<Genre> genreDao;
  private final IdNameDao<Rating> ratingDao;

  private final LikesDao likesDao;

  public FilmStorageDaoImpl(
    JdbcTemplate jdbcTemplate,
    GenresDao genresDao,
    IdNameDao<Genre> genreDao,
    IdNameDao<Rating> ratingDao,
    LikesDao likesDao
  ) {
    this.jdbcTemplate = jdbcTemplate;
    this.genresDao = genresDao;
    this.genreDao = genreDao;
    this.ratingDao = ratingDao;
    this.likesDao = likesDao;
  }

  @Override
  public List<Film> getListItems() {
    String sql = "SELECT * FROM  \"Film\"";
    return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
  }

  private Film makeFilm(ResultSet rs) throws SQLException {
    Integer id = rs.getInt("film_id");
    String name = rs.getString("name");
    String description = rs.getString("description");
    LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
    Integer duration = rs.getInt("duration");
    Integer ratingId = rs.getInt("rating_id");

    List<Integer> likes = likesDao.getAllLikes(id);

    Optional<Rating> ratingOptional = ratingDao.findItemById(ratingId);
    Rating rating = ratingOptional.orElse(null);

    List<Integer> genresIds = genresDao.getAllItems(id);
    Set<Genre> listGenre = new HashSet<>();

    for (Integer genreId : genresIds) {
      Optional<Genre> genreOptional = genreDao.findItemById(genreId);
      Genre genre = genreOptional.orElse(null);

      if (genre != null) listGenre.add(genre);
    }

    Film film = new Film();
    film.setId(id);
    film.setName(name);
    film.setDescription(description);
    film.setReleaseDate(releaseDate);
    film.setDuration(duration);
    film.setLikes(likes);
    film.setMpa(rating);
    film.setGenres(listGenre);

    return film;
  }

  @Override
  public Optional<Film> findItemById(Integer id) {
    SqlRowSet userSql = jdbcTemplate.queryForRowSet(
      "select * from \"Film\" where \"film_id\" = ?",
      id
    );

    if (userSql.next()) {
      Film film = new Film();
      film.setId(userSql.getInt("film_id"));
      film.setName(userSql.getString("name"));
      film.setDescription(userSql.getString("description"));
      film.setReleaseDate(userSql.getDate("release_date").toLocalDate());
      film.setDuration(userSql.getInt("duration"));

      List<Integer> likes = likesDao.getAllLikes(id);

      Optional<Rating> ratingOptional = ratingDao.findItemById(
        userSql.getInt("rating_id")
      );
      Rating rating = ratingOptional.orElse(null);

      List<Integer> genresIds = genresDao.getAllItems(id);
      Set<Genre> listGenre = new LinkedHashSet<>();

      for (Integer genreId : genresIds) {
        Optional<Genre> genreOptional = genreDao.findItemById(genreId);
        Genre genre = genreOptional.orElse(null);

        if (genre != null) listGenre.add(genre);
      }

      film.setLikes(likes);
      film.setMpa(rating);
      film.setGenres(listGenre);

      return Optional.of(film);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Film createItem(Film item) {
    String sqlQuery =
      "INSERT INTO \"Film\" (\"name\", \"description\", \"release_date\", \"duration\", \"rating_id\") " +
      " VALUES (?, ?, ?, ?, ?)";

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
      connection -> {
        PreparedStatement stmt = connection.prepareStatement(
          sqlQuery,
          new String[] { "film_id" }
        );
        stmt.setString(1, item.getName());
        stmt.setString(2, item.getDescription());
        stmt.setDate(3, Date.valueOf(item.getReleaseDate()));
        stmt.setInt(4, item.getDuration());
        stmt.setInt(5, item.getMpa().getId());

        return stmt;
      },
      keyHolder
    );

    item.setId(keyHolder.getKey().intValue());

    genresDao.deleteAllItem(item.getId());

    for (Genre genre : item.getGenres()) {
      genresDao.addItem(item.getId(), genre.getId());
    }

    return item;
  }

  @Override
  public Film updateItem(Film item) {
    String sqlQuery =
      "UPDATE  \"Film\" SET " +
      "\"name\" = ?, \"description\"  = ?, \"release_date\"  = ?, \"duration\"  = ?, \"rating_id\"  = ? " +
      "WHERE \"film_id\" = ?";
    jdbcTemplate.update(
      sqlQuery,
      item.getName(),
      item.getDescription(),
      item.getReleaseDate(),
      item.getDuration(),
      item.getMpa().getId(),
      item.getId()
    );

    genresDao.deleteAllItem(item.getId());

    for (Genre genre : item.getGenres()) {
      genresDao.addItem(item.getId(), genre.getId());
    }

    return findItemById(item.getId()).orElse(null);
  }
}
