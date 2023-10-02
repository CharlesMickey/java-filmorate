# java-filmorate

Template repository for Filmorate project.

DB scheme:

![DB scheme](https://github.com/CharlesMickey/java-filmorate/blob/main/src/images/bd%20scheme.svg)

# #  Пример запросов:

CREATE TABLE "Film" (
"film_id" int PRIMARY KEY,
"name" varchar NOT NULL,
"description" text,
"release_date" date,
"duration" int,
"rating_id" int
);

CREATE TABLE "MPAARating" (
"rating_id" int PRIMARY KEY,
"rating_name" varchar NOT NULL
);

CREATE TABLE "Genre" (
"genre_id" int PRIMARY KEY,
"genre_name" varchar NOT NULL
);

CREATE TABLE "FilmGenre" (
"film_genre_id" varchar(255) PRIMARY KEY,
"film_id" int,
"genre_id" int
);

CREATE TABLE "FilmLikes" (
"film_user_id" varchar(255) PRIMARY KEY,
"film_id" int,
"user_id" int
);

CREATE TABLE "User" (
"user_id" int PRIMARY KEY,
"email" varchar NOT NULL,
"login" varchar NOT NULL,
"name" varchar,
"birthday" date
);

CREATE TABLE "Friends" (
"user_user_id" varchar(255) PRIMARY KEY,
"follower_id" int,
"followed_id" int,
"status_id" int
);

CREATE TABLE "FriendshipStatus" (
"status_id" int PRIMARY KEY,
"name" varchar NOT NULL
);

COMMENT ON COLUMN "User"."birthday" IS 'Дата рождения';

ALTER TABLE "Film" ADD FOREIGN KEY ("rating_id") REFERENCES "MPAARating" ("rating_id");

ALTER TABLE "FilmGenre" ADD FOREIGN KEY ("film_id") REFERENCES "Film" ("film_id");

ALTER TABLE "FilmGenre" ADD FOREIGN KEY ("genre_id") REFERENCES "Genre" ("genre_id");

ALTER TABLE "FilmLikes" ADD FOREIGN KEY ("film_id") REFERENCES "Film" ("film_id");

ALTER TABLE "FilmLikes" ADD FOREIGN KEY ("user_id") REFERENCES "User" ("user_id");

ALTER TABLE "Friends" ADD FOREIGN KEY ("follower_id") REFERENCES "User" ("user_id");

ALTER TABLE "Friends" ADD FOREIGN KEY ("followed_id") REFERENCES "User" ("user_id");

ALTER TABLE "Friends" ADD FOREIGN KEY ("status_id") REFERENCES "FriendshipStatus" ("status_id");
