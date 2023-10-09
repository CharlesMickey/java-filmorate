-- Создание таблицы Film, если она не существует
CREATE TABLE
    IF NOT EXISTS "Film" (
        "film_id" int PRIMARY KEY,
        "name" varchar NOT NULL,
        "description" text,
        "release_date" date,
        "duration" int,
        "rating_id" int
    );

-- Создание таблицы MPAARating, если она не существует
CREATE TABLE
    IF NOT EXISTS "MPAARating" (
        "rating_id" int PRIMARY KEY,
        "rating_name" varchar NOT NULL
    );

-- Создание таблицы Genre, если она не существует
CREATE TABLE
    IF NOT EXISTS "Genre" (
        "genre_id" int PRIMARY KEY,
        "genre_name" varchar NOT NULL
    );

-- Создание таблицы FilmGenre, если она не существует
CREATE TABLE
    IF NOT EXISTS "FilmGenre" (
        "film_genre_id" varchar(255) PRIMARY KEY,
        "film_id" int,
        "genre_id" int
    );

-- Создание таблицы FilmLikes, если она не существует
CREATE TABLE
    IF NOT EXISTS "FilmLikes" (
        "film_user_id" varchar(255) PRIMARY KEY,
        "film_id" int,
        "user_id" int
    );

-- Создание таблицы User, если она не существует
CREATE TABLE
    IF NOT EXISTS "User" (
        "user_id" int generated by default as identity PRIMARY KEY,
        "email" varchar NOT NULL,
        "login" varchar NOT NULL,
        "name" varchar,
        "birthday" date
    );

-- Создание таблицы Friends, если она не существует
CREATE TABLE
    IF NOT EXISTS "Friends" (
        "user_user_id" varchar(255) PRIMARY KEY,
        "follower_id" int,
        "followed_id" int,
        "status_id" int
    );

-- Создание таблицы FriendshipStatus, если она не существует
CREATE TABLE
    IF NOT EXISTS "FriendshipStatus" (
        "status_id" int PRIMARY KEY,
        "name" varchar NOT NULL
    );

-- Добавление внешних ключей, если они не существуют
ALTER TABLE "Film" ADD FOREIGN KEY ("rating_id") REFERENCES "MPAARating" ("rating_id");

ALTER TABLE "FilmGenre" ADD FOREIGN KEY ("film_id") REFERENCES "Film" ("film_id");

ALTER TABLE "FilmGenre" ADD FOREIGN KEY ("genre_id") REFERENCES "Genre" ("genre_id");

ALTER TABLE "FilmLikes" ADD FOREIGN KEY ("film_id") REFERENCES "Film" ("film_id");

ALTER TABLE "FilmLikes" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("user_id");

ALTER TABLE "Friends" ADD FOREIGN KEY ("follower_id") REFERENCES "User" ("user_id");

ALTER TABLE "Friends" ADD FOREIGN KEY ("followed_id") REFERENCES "User" ("user_id");

ALTER TABLE "Friends" ADD FOREIGN KEY ("status_id") REFERENCES "FriendshipStatus" ("status_id");