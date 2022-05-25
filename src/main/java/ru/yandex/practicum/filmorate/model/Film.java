package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private int id;
    @NotNull
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Set<Integer> userLikes;
    private int rate;

    public Film(String name, String description, LocalDate releaseDate, int duration, int rate) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
        userLikes = new HashSet<>();
    }
}
