package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryUserStorage implements UserStorage {
    private Map<Integer, User> userHashMap = new HashMap<>();

    @Override
    public Map<Integer, User> getUsers() {
        return userHashMap;
    }

    public boolean isExist(int key) {
        return userHashMap.containsKey(key);
    }
    @Override
    public void add(Integer key, User value) {
        userHashMap.put(key, value);
    }
    @Override
    public void update(Integer key, User value) {
        userHashMap.replace(key, value);
    }
    @Override
    public void delete(Integer key) {
        userHashMap.remove(key);
    }
    @Override
    public Collection<User> getAll() {
        return userHashMap.values();
    }
    @Override
    public Optional<User> getById(Integer key) {
        return Optional.ofNullable(userHashMap.get(key));
    }
}
