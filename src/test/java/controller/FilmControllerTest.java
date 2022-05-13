package controller;

import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController filmController;

    @BeforeEach
    public void controllerCreate() {
        filmController = new FilmController();
    }

    @Test
    public void nameShouldNotBeEmpty() {
        //создаем объект film
        Film film = Film.builder()
                .name("")
                .id(1)
                .description("Фильм-1")
                .duration(Duration.ofMinutes(120))
                .releaseDate(LocalDate.of(1950,11,5))
                .build();

        //проверяем выброшенное исключение
        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        assertEquals("Название фильма должно быть непустым", exception.getMessage());
    }

    @Test
    public void descriptionShouldNotBeMore200() {
        //создаем объект film
        Film film = Film.builder()
                .name("Фильм")
                .id(1)
                .description("мне нужно сделать описание фильма более чем двести символов для проверки валидации поля " +
                        "description, поэтому я написал такое длинное описание для этого фильма, но недостаточно длинное" +
                        "потому что символов все еще не хватает, а вот уже хватает")
                .duration(Duration.ofMinutes(120))
                .releaseDate(LocalDate.of(1950,11,5))
                .build();

        //проверяем выброшенное исключение
        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        assertEquals("Описание не должно быть больше 200 символов", exception.getMessage());
    }

    @Test
    public void releaseDateShouldNotBeBefore1895() {
        //создаем объект film
        Film film = Film.builder()
                .name("Фильм")
                .id(1)
                .description("описание фильма не более чем двести символов")
                .duration(Duration.ofMinutes(120))
                .releaseDate(LocalDate.of(1850,11,5))
                .build();

        //проверяем выброшенное исключение
        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        assertEquals("Дата релиза фильма не должна быть раньше 28 декабря 1895 года", exception.getMessage());
    }

    @Test
    public void durationShouldNotBeBeforeNegative() {
        //создаем объект film
        Film film = Film.builder()
                .name("Фильм")
                .id(1)
                .description("описание фильма не более чем двести символов")
                .duration(Duration.ofMinutes(-1))
                .releaseDate(LocalDate.of(1950,11,5))
                .build();

        //проверяем выброшенное исключение
        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        assertEquals("Продолжительность фильма меньше нуля", exception.getMessage());
    }
}