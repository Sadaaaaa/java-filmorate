package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.mapper.LikesRowMapper;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;

@Repository
public class LikesDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addLikeDao(int id, int userId) {
        String sql = "INSERT INTO Likes (film_id, user_id) " + "VALUES (?, ?)";
        jdbcTemplate.update(sql, id, userId);
    }

    public void deleteLikeDao(int id, int userId) {
        // проверка есть лайк в базе или нет
        String sqlChecker = "SELECT * FROM LIKES WHERE film_id = ? AND user_id = ?";
        try {
            jdbcTemplate.queryForObject(sqlChecker, new LikesRowMapper(), id, userId);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("Like not found!");
        }

        // удаляем лайк с фильма
        String sqlDelete = "DELETE FROM Likes WHERE film_id = ? AND user_id = ?";
        try {
            boolean delLike = jdbcTemplate.update(sqlDelete, id, userId) > 0;
            if (delLike) {
                System.out.println("Лайк удален!");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("Wrong user id!");
        }
    }
}
