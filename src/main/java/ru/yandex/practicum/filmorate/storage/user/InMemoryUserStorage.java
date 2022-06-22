package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private Map<Integer, User> users = new HashMap<>();

    @Override
    public Map<Integer, User> getUsers() {
        return new HashMap<>(users);
    }

    public boolean isExist(int key) {
        return users.containsKey(key);
    }
    @Override
    public void add(Integer key, User value) {
        users.put(key, value);
    }
    @Override
    public void update(Integer key, User value) {
        users.replace(key, value);
    }
    @Override
    public void delete(Integer key) {
        users.remove(key);
    }
//    @Override
//    public List<User> getAll() {
//        return new ArrayList<>(users.values());
//    }
    @Override
    public Optional<User> getById(Integer key) {
        return Optional.ofNullable(users.get(key));
    }
}
