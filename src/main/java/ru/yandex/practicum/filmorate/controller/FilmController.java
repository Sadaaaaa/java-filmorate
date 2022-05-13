package ru.yandex.practicum.filmorate.controller;

import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private static final int MAX_DESCRIPTION_SYMBOLS = 200;
    private static final LocalDate OLDEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    //private final static Logger log = LoggerFactory.getLogger(FilmController.class);


    private Map<Integer, Film> filmHashMap = new HashMap<>();

    //добавление фильма
    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        if (isValidItem(film)) {
            filmHashMap.put(film.getId(), film);
            log.info("Фильм {} добавлен", film.getName());
        }
        return film;
    }

    //обновление фильма
    @PutMapping
    public void updateFilm(@RequestBody Film film) {
        if (filmHashMap.containsKey(film.getId()) && isValidItem(film)) {
            filmHashMap.replace(film.getId(), film);
            log.info("Фильм {} обновлен", film.getName());
        }
    }

    //получение всех фильмов
    @GetMapping
    public Map<Integer, Film> getFilmHashMap() {
        return filmHashMap;
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
        if (film.getDuration().isNegative()) {
            log.warn("Продолжительность фильма меньше нуля");
            throw new ValidationException("Продолжительность фильма меньше нуля");
        }

        return true;
    }

}
