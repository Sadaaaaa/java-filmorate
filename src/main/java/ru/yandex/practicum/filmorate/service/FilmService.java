package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private FilmStorage inMemoryFilmStorage;
    private UserStorage inMemoryUserStorage;
    private static final int MAX_DESCRIPTION_SYMBOLS = 200;
    private static final LocalDate OLDEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private int filmsCount;

    @Autowired
    public FilmService(FilmStorage inMemoryFilmStorage, UserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    //добавление фильма
    public Film addFilm(Film film) {
        if (isValidItem(film)) {
            filmsCount++;
            film.setId(filmsCount);
            inMemoryFilmStorage.add(film.getId(), film);
            log.info("Фильм {} добавлен", film.getName());
        }
        return film;
    }

    //обновление фильма
    public Film updateFilm(Film film) {
        //вызывается для проверки существования объекта, если не существует - выбросится исключение в методе getFilm()
        getFilm(film.getId());

        if (isValidItem(film)) {
            inMemoryFilmStorage.update(film.getId(), film);
            log.info("Фильм {} обновлен", film.getName());
        }

        return inMemoryFilmStorage.getFilms().get(film.getId());
    }

    //получение всех фильмов
    public Map<Integer, Film> getFilms() {
        return inMemoryFilmStorage.getFilms();
    }

    public void addLike(int id, int userId) {
        Film film = getFilm(id);
        film.getUserLikes().add(userId);
    }

    public void deleteLike(int id, int userId) {
        inMemoryUserStorage.getById(userId).orElseThrow(() -> new UserNotFoundException("ID юзера не найден."));

        Film film = getFilm(id);
        film.getUserLikes().remove(userId);
    }

    //получение перечня популярных фильмов
    public List<Film> getPopularFilms(int count) {
        return inMemoryFilmStorage.getFilms().values().stream()
                .sorted((o1, o2) -> o2.getUserLikes().size() - o1.getUserLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    //получение фильма по id
    public Film getFilm(int id) {
        return inMemoryFilmStorage.getById(id).orElseThrow(()->
                new FilmNotFoundException("Фильм с идентификатором " + id + " не найден"));
    }

    public boolean isValidItem(Film film) {
        //название не может быть пустым
        if (film.getName().isEmpty()) {
            log.warn("Название фильма - пусто");
            throw new ValidationException("Название фильма должно быть непустым");
        }

        //максимальная длина описания — 200 символов
        if (film.getDescription().length() > MAX_DESCRIPTION_SYMBOLS) {
            log.warn("Описание фильма содержит более 200 символов");
            throw new ValidationException("Описание не должно быть больше 200 символов");
        }

        //максимальная длина описания — 200 символов
        if (film.getDescription().isBlank()) {
            log.warn("Описание фильма не содержит символов");
            throw new ValidationException("Описание не должно быть пустым");
        }

        //дата релиза — не раньше 28 декабря 1895 года
        if (film.getReleaseDate().isBefore(OLDEST_RELEASE_DATE)) {
            log.warn("Дата релиза раньше 28.12.1895");
            throw new ValidationException("Дата релиза фильма не должна быть раньше 28 декабря 1895 года");
        }

        //продолжительность фильма должна быть положительной
        if (film.getDuration() < 0) {
            log.warn("Продолжительность фильма меньше нуля");
            throw new ValidationException("Продолжительность фильма меньше нуля");
        }

        return true;
    }

}
