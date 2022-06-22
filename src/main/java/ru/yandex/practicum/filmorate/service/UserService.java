package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendsDao;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class UserService {
    private UserStorage userStorage;
    private int usersCount;

    private FriendsDao friendsDao;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage, FriendsDao friendsDao) {
        this.userStorage = userStorage;
        this.friendsDao = friendsDao;
    }

    //создание пользователя
    public User addUser(User user) {
        if (isValidItem(user)) {
            usersCount++;
            user.setId(usersCount);
            userStorage.add(user.getId(), user);
            log.info("Пользователь {} добавлен", user.getLogin());
        }
        return user;
    }

    //обновление пользователя
    public User updateUser(User user) {
        //вызывается для проверки существования объекта, если не существует - выбросится исключение в методе getUser()
        getUser(user.getId());

        User userUpd = null;
        if (isValidItem(user)) {
            userStorage.update(user.getId(), user);
            userUpd = getUser(user.getId());
            log.info("Пользователь {} обновлен", userUpd.getLogin());
        }

        return userUpd;
    }

    //получение списка всех пользователей
    public Map<Integer, User> getUserHashMap() {
        return userStorage.getUsers();
    }


    //добавление пользователя в друзья
    public void addFriend(int id, int friendId) {
        // Хранение в DataBase
        friendsDao.addFriendDao(id, friendId);
    }

    //удаление из друзей
    public void deleteFriend(int id, int friendId) {
        // Хранение в DataBase
        friendsDao.deleteFriendDao(id, friendId);
    }

    //получение списка всех друзей пользователя
    public List<User> getFriendList(int id) {
        // Хранение в DataBase
        return friendsDao.getFriendListDao(id);
    }

    //получение списка общих друзей двух пользователей
    public List<User> getCommonFriendList(int id, int otherId) {
        // Хранение в DataBase
        return friendsDao.getCommonFriendListDao(id, otherId);
    }

    //получение юзера по id
    public User getUser(int id) {
        return userStorage.getById(id).orElseThrow(() -> new UserNotFoundException("ID юзера не найден."));
        //return userStorage.getUsers().get(id);
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
