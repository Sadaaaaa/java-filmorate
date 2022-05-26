package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Map<Integer, Film> filmHashMap = new HashMap<>();

    @Override
    public Map<Integer, Film> getFilms() {
        return filmHashMap;
    }

    public boolean isExist(int key) {
        return filmHashMap.containsKey(key);
    }
    @Override
    public void add(Integer key, Film value) {
        filmHashMap.put(key, value);
    }
    @Override
    public void update(Integer key, Film value) {
        filmHashMap.replace(key, value);
    }
    @Override
    public void delete(Integer key) {
        filmHashMap.remove(key);
    }
    @Override
    public Collection<Film> getAll() {
        return filmHashMap.values();
    }
    @Override
    public Optional<Film> getById(Integer key) {
        return Optional.ofNullable(filmHashMap.get(key));
    }

}
