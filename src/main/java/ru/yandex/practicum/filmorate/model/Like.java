package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Like {
    int film_id;
    int user_id;
}
