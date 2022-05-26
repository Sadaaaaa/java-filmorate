package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private UserStorage inMemoryUserStorage;
    private int usersCount;

    @Autowired
    public UserService(UserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    //создание пользователя
    public User addUser(User user) {
        if (isValidItem(user)) {
            usersCount++;
            user.setId(usersCount);
            inMemoryUserStorage.getUsers().put(user.getId(), user);
            log.info("Пользователь {} добавлен", user.getLogin());
        }
        return user;
    }

    //обновление пользователя
    public User updateUser(User user) {
        inMemoryUserStorage.getById(user.getId()).orElseThrow(() -> new UserNotFoundException("ID юзера не найден."));

        User userUpd = null;
        if (isValidItem(user)) {
            inMemoryUserStorage.update(user.getId(), user);
            userUpd = inMemoryUserStorage.getUsers().get(user.getId());
            log.info("Пользователь {} обновлен", userUpd.getLogin());
        }

        return userUpd;
    }

    //получение списка всех пользователей
    public Map<Integer, User> getUserHashMap() {
        return inMemoryUserStorage.getUsers();
    }



    //добавление пользователя в друзья
    public void addFriend(int id, int friendId) {
        User user = inMemoryUserStorage.getById(id).orElseThrow(() ->
                new UserNotFoundException("Юзер с ID " + id + " не найден."));
        User friend = inMemoryUserStorage.getById(friendId).orElseThrow(() ->
                new UserNotFoundException("Юзер с ID " + friendId + " не найден."));

        user.getFriends().add(friendId);
        friend.getFriends().add(id);
    }

    //удаление из друзей
    public void deleteFriend(int id, int friendId) {
        User user = inMemoryUserStorage.getById(id).orElseThrow(() ->
                new UserNotFoundException("Юзер с ID " + id + " не найден."));
        User friend = inMemoryUserStorage.getById(friendId).orElseThrow(() ->
                new UserNotFoundException("Юзер с ID " + friendId + " не найден."));

        user.getFriends().remove(friendId);
        friend.getFriends().remove(id);
    }

    //получение списка всех друзей пользователя
    public List<User> getFriendList(int id) {
        User user = inMemoryUserStorage.getById(id).orElseThrow(() ->
                new UserNotFoundException("Юзер с ID " + id + " не найден."));

        Set<Integer> hashSet = user.getFriends();

        return hashSet.stream()
                .map(x -> inMemoryUserStorage.getUsers().get(x))
                .collect(Collectors.toList());
    }

    //получение списка общих друзей двух пользователей
    public List<User> getCommonFriendList(int id, int otherId) {
        User user = inMemoryUserStorage.getById(id).orElseThrow(() ->
                new UserNotFoundException("Юзер с ID " + id + " не найден."));
        User friend = inMemoryUserStorage.getById(otherId).orElseThrow(() ->
                new UserNotFoundException("Юзер с ID " + otherId + " не найден."));

        Set<Integer> matchFriends = new HashSet<>(user.getFriends());
        matchFriends.retainAll(friend.getFriends());

        return matchFriends.stream()
                .map(x -> inMemoryUserStorage.getUsers().get(x))
                .collect(Collectors.toList());
    }

    //получение юзера по id
    public User getUser(int id) {
        inMemoryUserStorage.getById(id).orElseThrow(() -> new UserNotFoundException("ID юзера не найден."));
        return inMemoryUserStorage.getUsers().get(id);
    }

    //валидация
    public boolean isValidItem(User user) {
        //электронная почта не может быть пустой и должна содержать символ @
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.warn("Электронная почта пуста или не содержит @");
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }

        //логин не может быть пустым и содержать пробелы
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.warn("Логин пуст или содержит пробелы");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }

        //имя для отображения может быть пустым — в таком случае будет использован логин
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.warn("Имя пользователя пусто");
            //throw new ValidationException("Имя может быть пустым, вместо имени будет использован логин");
        }

        //дата рождения не может быть в будущем
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Дата рождения пользователя - в будущем");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }

        return true;
    }
}
