package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.mapper.GenresRowMapper;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Genres;

import java.util.List;

@Repository
public class GenresDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenresDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Genres getGenresDao(int id) {
        String sql = "SELECT * FROM GENRE WHERE GENRE_ID = ?";
        try {
            return jdbcTemplate.query(sql, new GenresRowMapper(), id).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new FilmNotFoundException("Genre id is not correct!");
        }
    }

    public List<Genres> getAllGenresDao() {
        String sql = "SELECT * FROM GENRE";
        return jdbcTemplate.query(sql, new GenresRowMapper());
    }
}
