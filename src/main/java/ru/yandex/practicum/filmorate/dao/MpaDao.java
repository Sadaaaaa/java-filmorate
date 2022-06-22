package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.mapper.MpaRowMapper;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Repository
public class MpaDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Mpa getMpaDao(int id) {
        String sql = "SELECT * FROM mpa WHERE mpa_id = ?";
        try {
            return jdbcTemplate.query(sql, new MpaRowMapper(), id).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new FilmNotFoundException("MPA ID is not correct!");
        }
    }

    public List<Mpa> getAllMpaDao() {
        String sql = "SELECT * FROM mpa";
        return jdbcTemplate.query(sql, new MpaRowMapper());
    }
}
