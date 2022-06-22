package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.dao.mapper.GenresRowMapper;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final FilmGenreDao filmGenreDao;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, FilmGenreDao filmGenreDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmGenreDao = filmGenreDao;
    }

    @Override
    public Map<Integer, Film> getFilms() {
        String sqlFilms = "SELECT * FROM Films AS f JOIN Mpa AS m ON f.film_mpaid = m.mpa_id";
        List<Film> filmsList = jdbcTemplate.query(sqlFilms, new FilmRowMapper());

        String sqlLikes = "SELECT film_id, COUNT(user_id) AS likes FROM Likes GROUP BY film_id";
        Map<Integer, Integer> likes = jdbcTemplate.query(sqlLikes, rs -> {
            Map<Integer, Integer> filmsAndLikes = new HashMap<>();
            while (rs.next()) {
                filmsAndLikes.put(rs.getInt("film_id"), rs.getInt("likes"));
            }
            return filmsAndLikes;
        });

        for (Film x : filmsList) {
            if (likes.containsKey(x.getId())) {
                x.setUserLikes(likes.get(x.getId()));
            } else {
                x.setUserLikes(0);
            }
        }
        return filmsList.stream().collect(Collectors.toMap(Film::getId, film -> film));
    }

    @Override
    public void add(Integer key, Film value) {
        String sqlFilm = "INSERT INTO films (film_id, film_name, film_description, film_releaseDate, film_duration, FILM_MPAID) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlFilm,
                key,
                value.getName(),
                value.getDescription(),
                value.getReleaseDate(),
                value.getDuration(),
                value.getMpa().getId());

        if (value.getGenres() != null) {
            String sqlGenre = "INSERT INTO films_genre (film_id, genre_id) " +
                    "VALUES (?, ?)";
            for (int i = 0; i < value.getGenres().length; i++) {
                jdbcTemplate.update(sqlGenre,
                        key,
                        value.getGenres()[i].getId());
            }
        }
    }

    @Override
    public Film update(Integer key, Film value) {

        String sqlFind = "SELECT film_id FROM Films WHERE film_id = ?";
        List<Integer> listFind = jdbcTemplate.query(sqlFind, (rs, rowNum) -> rs.getInt(1), key);
        if (listFind.size() == 0) {
            throw new FilmNotFoundException("Film ID not found!");
        }

        String sqlUpdateFilm = "UPDATE Films SET " +
                "film_name = ?, film_description = ?, film_releaseDate = ?, film_duration = ?, film_mpaID = ?" +
                "WHERE film_id = ?";
        jdbcTemplate.update(sqlUpdateFilm,
                value.getName(),
                value.getDescription(),
                value.getReleaseDate(),
                value.getDuration(),
                value.getMpa().getId(),
                key);

        if (value.getGenres() != null) {
            filmGenreDao.update(key, value);

            if (value.getGenres().length == 0) {
                return value;
            }
        }
        return getById(key).get();
    }

    @Override
    public void delete(Integer key) {
        String sql = "DELETE FROM Films WHERE film_id = ?";
        jdbcTemplate.update(sql, key);
    }

    @Override
    public Optional<Film> getById(Integer key) {

        String sql = "SELECT * FROM Films AS f JOIN Mpa AS m ON f.film_mpaid = m.mpa_id WHERE film_id = ? ";

        String sqlGenres = "select * from Films_genre AS fg join Genre AS g on g.genre_id = fg.genre_id where film_id = ? ORDER BY g.genre_id";
        List<Genres> genresList = jdbcTemplate.query(sqlGenres, new GenresRowMapper(), key);

        Genres[] genresArray = new Genres[genresList.size()];
        for (int i = 0; i < genresList.size(); i++) {
            genresArray[i] = genresList.get(i);
        }

        if (genresArray.length == 0) {
            genresArray = null;
        }

        try {
            Film film = jdbcTemplate.queryForObject(sql, new FilmRowMapper(), key);
            assert film != null;
            film.setGenres(genresArray);
            return Optional.of(film);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("User ID not found!");
            return Optional.empty();
        }
    }
}
