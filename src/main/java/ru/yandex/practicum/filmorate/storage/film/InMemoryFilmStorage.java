package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Map<Integer, Film> films = new HashMap<>();

    @Override
    public Map<Integer, Film> getFilms() {
        return new HashMap<>(films);
    }

    public boolean isExist(int key) {
        return films.containsKey(key);
    }
    @Override
    public void add(Integer key, Film value) {
        films.put(key, value);
    }
    @Override
    public Film update(Integer key, Film value) {
        return films.replace(key, value);
    }
    @Override
    public void delete(Integer key) {
        films.remove(key);
    }
//    @Override
//    public List<Film> getAll() {
//        return new ArrayList<>(films.values());
//    }
    @Override
    public Optional<Film> getById(Integer key) {
        return Optional.ofNullable(films.get(key));
    }

}
