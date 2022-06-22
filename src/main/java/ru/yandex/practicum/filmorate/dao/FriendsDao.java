package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.mapper.EnumConverter;
import ru.yandex.practicum.filmorate.dao.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Repository
public class FriendsDao {
    private final JdbcTemplate jdbcTemplate;
    private EnumConverter enumConverter = new EnumConverter();

    @Autowired
    public FriendsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFriendDao(int id, int friendId) {
        if (id < 0 || friendId < 0) {
            throw new UserNotFoundException("Bad user ID!");
        }

        // ищем, есть ли первичный ключ friend_id + user_id
        String sqlFind = "SELECT * FROM friends WHERE user_id = ? AND friend_id = ?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlFind, friendId, id);

        if (list.size() == 0) { //если нет, то status 0 - "заявка в друзья"
            String sqlAdd = "INSERT INTO friends (user_id, friend_id, status) " +
                    "VALUES (?, ?, ?)";
            jdbcTemplate.update(sqlAdd, id, friendId, enumConverter.convertToDatabaseColumn(FriendshipStatus.REQUEST));
        } else {
            //если есть, то status 1 - "дружба"
            String sqlAdd1 = "INSERT INTO friends (user_id, friend_id, status) " +
                    "VALUES (?, ?, ?)";
            jdbcTemplate.update(sqlAdd1, id, friendId, enumConverter.convertToDatabaseColumn(FriendshipStatus.FRIENDSHIP));

            //не забываем другу поменять на status 1 - "дружба"
            String sqlAdd2 = "UPDATE friends SET " + "user_id = ?, friend_id = ?, status = ?";
            jdbcTemplate.update(sqlAdd2, friendId, id, enumConverter.convertToDatabaseColumn(FriendshipStatus.FRIENDSHIP));
        }
    }

    public List<User> getCommonFriendListDao(int id, int otherId) {
        String sqlFriend = "SELECT friend_id FROM friends WHERE user_id = ?";
        List<Integer> friendsUser = new ArrayList<>();
        List<Integer> friendsFriend;

        try {
            friendsUser = jdbcTemplate.query(sqlFriend, (rs, rowNum) -> rs.getInt(1), id);
            friendsFriend = jdbcTemplate.query(sqlFriend, (rs, rowNum) -> rs.getInt(1), otherId);
            friendsUser.retainAll(friendsFriend);
            friendsUser.addAll(friendsFriend);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("bad user id");
        }

//      Запрос с использованием параметров, хранящихся в ArrayList
//      https://www.baeldung.com/spring-jdbctemplate-in-list
        String inSql = String.join(",", Collections.nCopies(friendsUser.size(), "?"));
        String sqlCommonFriends = String.format("SELECT * FROM users WHERE user_id IN (%s)", inSql);

        return jdbcTemplate.query(
                sqlCommonFriends,
                friendsUser.toArray(),
                new UserRowMapper());
    }


    public List<User> getFriendListDao(int id) {
        String str = enumConverter.convertToDatabaseColumn(FriendshipStatus.REQUEST);
        String sql = "SELECT friend_id FROM friends WHERE user_id = ? AND status = ?";

        List<Integer> friendListIds = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt(1), id, str);

//      Запрос с использованием параметров, хранящихся в ArrayList
//      https://www.baeldung.com/spring-jdbctemplate-in-list
        String inSql = String.join(",", Collections.nCopies(friendListIds.size(), "?"));
        String sqlCommonFriends = String.format("SELECT * FROM users WHERE user_id IN (%s)", inSql);

        return jdbcTemplate.query(
                sqlCommonFriends,
                friendListIds.toArray(),
                new UserRowMapper());
    }

    public void deleteFriendDao(int id, int friendId) {
        String sql = "DELETE FROM friends where user_id = ? AND friend_id = ?";
        boolean delFriend = jdbcTemplate.update(sql, id, friendId) > 0;
    }
}





