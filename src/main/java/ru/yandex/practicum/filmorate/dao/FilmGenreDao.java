package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.mapper.FilmsGenreRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genres;

import java.util.*;

@Repository
public class FilmGenreDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmGenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<FilmGenre> update(int filmId, Film film) {
        delete(filmId);

        Set<Genres> newSet = new HashSet<>();
        if(film.getGenres().length != 0) {
            newSet.addAll(Arrays.asList(film.getGenres()));
        }
        List<Genres> arrGenre = new ArrayList<>(newSet);

        for (int i = 0; i < arrGenre.size(); i++) {
            jdbcTemplate.update("INSERT INTO FILMS_GENRE (FILM_ID, GENRE_ID)" +
                            "VALUES (?, ?)",
                    filmId,
                    arrGenre.get(i).getId());
        }

        return jdbcTemplate.query(
                "SELECT * FROM FILMS_GENRE WHERE FILM_ID = ?",
                new FilmsGenreRowMapper(),
                filmId);
    }

    public boolean delete(int filmId) {
        String sql = "DELETE FROM FILMS_GENRE WHERE FILM_ID = ?";
        return jdbcTemplate.update(sql, filmId) > 0;
    }
}
