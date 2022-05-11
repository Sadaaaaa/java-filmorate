package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> userHashMap = new HashMap<>();
    //private final static Logger log = LoggerFactory.getLogger(UserController.class);

    //создание пользователя
    @PostMapping
    public User addUser(@RequestBody User user) {
        if (isValidItem(user)) {
            userHashMap.put(user.getId(), user);
            log.info("Пользователь {} добавлен", user.getLogin());
        }
        return user;
    }

    //обновление пользователя
    @PutMapping
    public void updateUser(@RequestBody User user) {
        if (userHashMap.containsKey(user.getId()) && isValidItem(user)) {
            userHashMap.replace(user.getId(), user);
            log.info("Пользователь {} обновлен", user.getLogin());
        }
    }

    //получение списка всех пользователей
    @GetMapping
    public Map<Integer, User> getUserHashMap() {
        return userHashMap;
    }

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
        if (user.getName().isEmpty()) {
            log.warn("Имя пользователя пусто");
            user.setName(user.getLogin());
            throw new ValidationException("Имя может быть пустым, вместо имени будет использован логин");
        }

        //дата рождения не может быть в будущем
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Дата рождения пользователя - в будущем");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }

        return true;
    }

}
