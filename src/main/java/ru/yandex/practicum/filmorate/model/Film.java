package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;

import java.time.Duration;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class Film {
    private int id;
    @NotNull
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Duration duration;
}
