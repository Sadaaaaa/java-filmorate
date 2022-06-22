package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface UserStorage {
    Map<Integer, User> getUsers();
//    boolean isExist(int item);
    void add(Integer key, User value);
    void update(Integer key, User value);
    void delete(Integer key);
//    Collection<User> getAll();
    Optional<User> getById(Integer key);
}
