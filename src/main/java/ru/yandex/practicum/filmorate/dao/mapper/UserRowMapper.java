package ru.yandex.practicum.filmorate.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("user_id"));
        user.setName(rs.getString("user_name"));
        user.setLogin(rs.getString("user_login"));
        user.setEmail(rs.getString("user_email"));
        user.setBirthday(rs.getDate("user_birthday").toLocalDate());

        return user;
    }
}
