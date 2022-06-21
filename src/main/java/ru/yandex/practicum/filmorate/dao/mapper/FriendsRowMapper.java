package ru.yandex.practicum.filmorate.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Friends;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendsRowMapper implements RowMapper<Friends> {

    @Override
    public Friends mapRow(ResultSet rs, int rowNum) throws SQLException {
        Friends friends = new Friends();

        friends.setId(rs.getInt("user_id"));
        friends.setFriend(rs.getInt("friend_id"));
        friends.setStatus(rs.getInt("status"));

        return friends;
    }
}