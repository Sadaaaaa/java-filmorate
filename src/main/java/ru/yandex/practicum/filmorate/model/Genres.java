package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Genres {
    int id;
    String name;

    public Genres(int id) {
        this.id = id;
    }

    public Genres() {

    }

}
