package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("ru.yandex.practicum.filmorate.controller")
public class ErrorHandler {

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public Map<String, String> handle(final UserNotFoundException e) {
//        return Map.of(
//                "errorMessage", e.getMessage()
//        );
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public Map<String, String> handle(final FilmNotFoundException e) {
//        return Map.of(
//                "errorMessage", e.getMessage()
//        );
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Map<String, String> handle(final ValidationException e) {
//        return Map.of(
//                "errorMessage", e.getMessage()
//        );
//    }

    @ExceptionHandler
    public ResponseEntity<?> handle(final UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handle(final FilmNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handle(final ValidationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
