package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class Friends {
    private int id;
    private int friend;
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;
//    private int status;
}
