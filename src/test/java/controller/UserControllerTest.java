//package controller;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.yandex.practicum.filmorate.controller.UserController;
//import ru.yandex.practicum.filmorate.exception.ValidationException;
//import ru.yandex.practicum.filmorate.model.User;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserControllerTest {
//    UserController userController;
//
//    @BeforeEach
//    public void controllerCreate() {
//        userController = new UserController();
//    }
//
//
//    @Test
//    public void emailShouldNotBeEmpty() {
//        User user = User.builder()
//                .id(1)
//                .email("")
//                .login("destroyer123")
//                .name("vasya")
//                .birthday(LocalDate.of(2005,5,12))
//                .build();
//
//        //проверяем выброшенное исключение
//        ValidationException exception = assertThrows(ValidationException.class, () -> userController.addUser(user));
//        assertEquals("Электронная почта не может быть пустой и должна содержать символ @", exception.getMessage());
//
//        user.setEmail("email.mail.com");
//        assertEquals("Электронная почта не может быть пустой и должна содержать символ @", exception.getMessage());
//    }
//
//    @Test
//    public void loginShouldNotBeEmptyOrWithSpaces() {
//        User user = User.builder()
//                .id(1)
//                .email("email@email.com")
//                .login("")
//                .name("vasya")
//                .birthday(LocalDate.of(2005,5,12))
//                .build();
//
//        //проверяем выброшенное исключение
//        ValidationException exception = assertThrows(ValidationException.class, () -> userController.addUser(user));
//        assertEquals("Логин не может быть пустым и содержать пробелы", exception.getMessage());
//
//        user.setLogin("destroyer 123");
//        assertEquals("Логин не может быть пустым и содержать пробелы", exception.getMessage());
//    }
//
//    @Test
//    public void nameShouldEqualsLoginIfBlank() {
//        User user = User.builder()
//                .id(1)
//                .email("email@email.com")
//                .login("destroyer123")
//                .name("")
//                .birthday(LocalDate.of(2005,5,12))
//                .build();
//
//        //проверяем выброшенное исключение
//        ValidationException exception = assertThrows(ValidationException.class, () -> userController.addUser(user));
//        assertEquals("Имя может быть пустым, вместо имени будет использован логин", exception.getMessage());
//
//        //проверяем, что логин и имя равны
//        userController.addUser(user);
//        assertEquals("destroyer123", userController.getUserHashMap().get(1).getName());
//    }
//
//    @Test
//    public void birthdayShouldNotBeInFuture() {
//        User user = User.builder()
//                .id(1)
//                .email("email@email.com")
//                .login("destroyer123")
//                .name("vasya")
//                .birthday(LocalDate.now().plusDays(1))
//                .build();
//
//        //проверяем выброшенное исключение
//        ValidationException exception = assertThrows(ValidationException.class, () -> userController.addUser(user));
//        assertEquals("Дата рождения не может быть в будущем", exception.getMessage());
//    }
//}