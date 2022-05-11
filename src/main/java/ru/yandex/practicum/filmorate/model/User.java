package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class User {
    private int id;
    @NotNull
    private String email;
    @NotNull
    private String login;
    private String name;
    private LocalDate birthday;
}
