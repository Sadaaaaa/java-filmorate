package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Setter
public class Film {
    private int id;
    @NotNull
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Integer userLikes;
    private int rate;
    private Genres[] genres;
    private Mpa mpa;

    public Film(String name, String description, LocalDate releaseDate, int duration, int rate, Genres[] genres, Mpa mpa) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
        this.genres = genres;
        this.mpa = mpa;
    }

    public Film() {

    }
}
