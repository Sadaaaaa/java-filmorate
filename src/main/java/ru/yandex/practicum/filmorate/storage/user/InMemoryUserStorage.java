package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private Map<Integer, User> userHashMap = new HashMap<>();

    public Map<Integer, User> getUserHashMap() {
        return userHashMap;
    }

    public void setUserHashMap(Map<Integer, User> userHashMap) {
        this.userHashMap = userHashMap;
    }
}
