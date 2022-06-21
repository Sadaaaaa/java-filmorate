package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<Integer, User> getUsers() {
        String sql = "SELECT * FROM users";

        return jdbcTemplate.query(sql, new UserRowMapper())
                .stream()
                .collect(Collectors.toMap(User::getId, user -> user));
    }

    @Override
    public boolean isExist(int item) {
        return false;
    }

    @Override
    public void add(Integer key, User value) {
        String sql = "INSERT INTO users (user_name, user_login, user_email, user_birthday) "
                + "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                value.getName(),
                value.getLogin(),
                value.getEmail(),
                value.getBirthday());
    }

    @Override
    public void update(Integer key, User value) {
        String sql = "UPDATE users SET " +
                "user_name = ?, user_login = ?, user_email = ?, user_birthday = ? " +
                "WHERE user_id = ?";
        jdbcTemplate.update(sql,
                value.getName(),
                value.getLogin(),
                value.getEmail(),
                value.getBirthday(),
                key);
    }

    @Override
    public void delete(Integer key) {

    }

    @Override
    public Collection<User> getAll() {
        return null;
    }

    @Override
    public Optional<User> getById(Integer key) {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UserRowMapper(), key));
        } catch (EmptyResultDataAccessException e) {
            System.out.println("user id not found");
            return Optional.empty();
        }
    }
}
