package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //создание пользователя
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    //обновление пользователя
    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    //получение списка всех пользователей
    @GetMapping
    public Collection<User> getUsers() {
        return userService.getUserHashMap().values();
    }

    //добавление пользователя в друзья
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
    }

    //удаление из друзей
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.deleteFriend(id, friendId);
    }

    //получение списка всех друзей пользователя
    @GetMapping("/{id}/friends")
    public List<User> getFriendList(@PathVariable int id) {
        return userService.getFriendList(id);
    }

    //получение списка общих друзей двух пользователей
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriendList(@PathVariable int id, @PathVariable int otherId) {
        return userService.getCommonFriendList(id, otherId);
    }

    //получение пользователя по заданному id
    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUser(id);
    }
}
