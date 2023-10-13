package ru.yandex.practicum.filmorate.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.StorageDao;
import ru.yandex.practicum.filmorate.model.User;

@Component
public class UserStorageDaoImpl implements StorageDao<User> {

  private final JdbcTemplate jdbcTemplate;

  public UserStorageDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<User> getListItems() {
    String userSql = "SELECT * FROM \"User\"";

    return jdbcTemplate.query(userSql, (rs, rowNum) -> makeUser(rs));
  }

  @Override
  public Optional<User> findItemById(Integer id) {
    SqlRowSet userSql = jdbcTemplate.queryForRowSet(
      "select * from \"User\" where \"user_id\" = ?",
      id
    );

    if (userSql.next()) {
      User user = new User();
      user.setId(userSql.getInt("user_id"));
      user.setEmail(userSql.getString("email"));
      user.setLogin(userSql.getString("login"));
      user.setName(userSql.getString("name"));
      user.setBirthday(userSql.getDate("birthday").toLocalDate());
      return Optional.of(user);
    } else {
      return Optional.empty();
    }
  }

  private User makeUser(ResultSet rs) throws SQLException {
    Integer id = rs.getInt("user_id");
    String email = rs.getString("email");
    String login = rs.getString("login");

    String name = rs.getString("name");
    LocalDate birthday = rs.getDate("birthday").toLocalDate();

    User user = new User();
    user.setId(id);
    user.setEmail(email);
    user.setLogin(login);
    user.setName(name);
    user.setBirthday(birthday);

    return user;
  }

  @Override
  public User createItem(User item) {
    String sqlQuery =
      "INSERT INTO \"User\" (\"email\", \"login\", \"name\", \"birthday\") VALUES (?, ?, ?, ?)";

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
      connection -> {
        PreparedStatement stmt = connection.prepareStatement(
          sqlQuery,
          new String[] { "user_id" }
        );
        stmt.setString(1, item.getEmail());
        stmt.setString(2, item.getLogin());
        stmt.setString(3, item.getName());
        stmt.setDate(4, Date.valueOf(item.getBirthday()));
        return stmt;
      },
      keyHolder
    );
    item.setId(keyHolder.getKey().intValue());
    return item;
  }

  @Override
  public User updateItem(User item) {
    String sqlQuery =
      "UPDATE  \"User\" SET \"email\" = ?, \"login\"  = ?, \"name\"  = ?, \"birthday\"  = ?" +
      "WHERE \"user_id\" = ?";
    jdbcTemplate.update(
      sqlQuery,
      item.getEmail(),
      item.getLogin(),
      item.getName(),
      item.getBirthday(),
      item.getId()
    );
    return item;
  }
}
