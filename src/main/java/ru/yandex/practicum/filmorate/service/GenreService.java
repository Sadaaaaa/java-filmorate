package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenresDao;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.model.Genres;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Service
@Slf4j
public class GenreService {
    private final GenresDao genresDao;

    @Autowired
    public GenreService(GenresDao genresDao) {
        this.genresDao = genresDao;
    }

    public Genres getGenre(int id) {
        return genresDao.getGenresDao(id);
    }

    public List<Genres> getAllGenre() {
        return genresDao.getAllGenresDao();
    }

}
