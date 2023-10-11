package ru.yandex.practicum.filmorate.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FriendsDao;

@Component
public class FriendsDaoImpl implements FriendsDao {

  private final JdbcTemplate jdbcTemplate;

  public FriendsDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Integer> getAllFriends(Integer userId) {
    List<Integer> friendsIds = new ArrayList<>();

    SqlRowSet userSql = jdbcTemplate.queryForRowSet(
      "SELECT * FROM \"Friends\" WHERE \"followed_id\" = ? OR (\"follower_id\" = ? )",
      userId,
      userId
    );

    while (userSql.next()) {
      int followedId = userSql.getInt("followed_id");
      int followerId = userSql.getInt("follower_id");

      if (followedId == userId && userSql.getInt("status_id") == 2) {
        friendsIds.add(followerId);
      }
      if (followerId == userId) {
        friendsIds.add(followedId);
      }
    }

    return friendsIds;
  }

  @Override
  public void addFriend(Integer userId, Integer friendId) {
    String checkSql1 =
      "SELECT COUNT(*) FROM \"Friends\" WHERE \"follower_id\" = ? AND \"followed_id\" = ?";
    String checkSql2 =
      "SELECT COUNT(*) FROM \"Friends\" WHERE \"follower_id\" = ? AND \"followed_id\" = ?";

    int count1 = jdbcTemplate.queryForObject(
      checkSql1,
      Integer.class,
      userId,
      friendId
    );
    int count2 = jdbcTemplate.queryForObject(
      checkSql2,
      Integer.class,
      friendId,
      userId
    );

    String sql;

    if (count1 > 0 || count2 > 0) {
      // Запись уже существует, устанавливаем статус 2 (подтвержденная заявка)
      sql =
        "UPDATE \"Friends\" SET \"status_id\" = 2" +
        "WHERE (\"follower_id\" = ? AND \"followed_id\" = ?) OR (\"follower_id\" = ? AND \"followed_id\" = ?)";
      jdbcTemplate.update(sql, userId, friendId, friendId, userId);
    } else {
      // Запись не существует, вставляем новую с статусом 1 (неподтвержденная заявка)
      sql =
        "INSERT INTO \"Friends\" (\"user_user_id\", \"follower_id\", \"followed_id\", \"status_id\") " +
        " VALUES (?, ?, ?, ?)";

      jdbcTemplate.update(sql, userId + "_" + friendId, userId, friendId, 1);
    }
  }

  @Override
  public void deleteFriend(Integer userId, Integer friendId) {
    String sql =
      "DELETE FROM \"Friends\" WHERE (\"follower_id\" = ? " +
      "AND \"followed_id\" = ?) OR (\"follower_id\" = ? AND \"followed_id\" = ?)";

    jdbcTemplate.update(sql, userId, friendId, friendId, userId);
  }
}
