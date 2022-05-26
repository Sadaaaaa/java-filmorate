package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface FilmStorage {
    Map<Integer, Film> getFilms();

    boolean isExist(int item);
    void add(Integer key, Film value);
    void update(Integer key, Film value);
    void delete(Integer key);
    Collection<Film> getAll();
    Optional<Film> getById(Integer key);
}
