package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Map<Integer, Film> filmHashMap = new HashMap<>();

    public Map<Integer, Film> getFilmHashMap() {
        return filmHashMap;
    }

    public void setFilmHashMap(Map<Integer, Film> filmHashMap) {
        this.filmHashMap = filmHashMap;
    }
}
