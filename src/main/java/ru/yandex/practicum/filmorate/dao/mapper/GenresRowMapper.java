package ru.yandex.practicum.filmorate.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genres;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenresRowMapper implements RowMapper<Genres> {

    @Override
    public Genres mapRow(ResultSet rs, int rowNum) throws SQLException {
        Genres genres = new Genres();

        genres.setId(rs.getInt("GENRE_ID"));
        genres.setName(rs.getString("GENRE_TITLE"));

        return genres;
    }
}
