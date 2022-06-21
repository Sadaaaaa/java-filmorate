drop table FILMS_GENRE;
drop table FILMS;
drop table FRIENDS;
drop table GENRE;
drop table LIKES;
drop table MPA;
drop table USERS;

create table IF NOT EXISTS Films
(
    film_id            int auto_increment,
    film_name          varchar(50) not null,
    film_description   varchar(200),
    film_releaseDate   date,
    film_duration      int,
    film_mpaID          int,
    constraint FILMS_PK
        primary key (film_id)
);

CREATE TABLE IF NOT EXISTS Users
(
    user_id          int auto_increment,
    user_name        varchar(50),
    user_login       varchar(50) not null,
    user_email       varchar(50) not null,
    user_birthday    date,
    constraint USERS_PK
        primary key (user_id)
    );

create table IF NOT EXISTS Likes
(
    film_id int,
    user_id int
--     constraint LIKES_FILMS_FILM_ID_FK
--         foreign key (film_id) references Films,
--     constraint LIKES_USERS_USER_ID_FK
--         foreign key (user_id) references Users
);

create table IF NOT EXISTS Mpa
(
    mpa_id     int,
    film_rating varchar(50)
--     constraint MPA_ID_FK
--         foreign key (mpa_id) references Films
);

create table IF NOT EXISTS Films_genre
(
    film_id    int,
    genre_id   INTEGER,
    constraint GENRE_FILMS_FILM_ID_FK
        foreign key (film_id) references Films
);

create table IF NOT EXISTS Genre
(
    genre_id    INTEGER,
    genre_title varchar(50)
--     CONSTRAINT Genre_PK
--         PRIMARY KEY (genre_id)
--     CONSTRAINT Genre_FK
--         foreign key (genre_id) references Films_genre
);

CREATE TABLE IF NOT EXISTS Friends
(
    user_id   INT,
    friend_id INT,
    status INT,
    CONSTRAINT friends_pk
        PRIMARY KEY (user_id, friend_id)
--     CONSTRAINT users_fk
--         FOREIGN KEY (user_id)
--             REFERENCES Users (user_id),
--     CONSTRAINT friend_fk
--         FOREIGN KEY (friend_id)
--             REFERENCES Users (user_id)
);

