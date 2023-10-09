INSERT INTO
    "MPAARating" ("rating_id", "rating_name")
VALUES
    (1, 'G'),
    (2, 'PG'),
    (3, 'PG-13'),
    (4, 'R'),
    (5, 'NC-17');

INSERT INTO
    "FriendshipStatus" ("status_id", "name")
VALUES
    (1, 'Неподтверждённая'),
    (2, 'Подтвержденная');

INSERT INTO
    "Genre" ("genre_id", "genre_name")
VALUES
    (1, 'Комедия'),
    (2, 'Драма'),
    (3, 'Мультфильм'),
    (4, 'Триллер'),
    (5, 'Документальный'),
    (6, 'Боевик');

--INSERT INTO
--    "Film" (
--        "film_id",
--        "name",
--        "description",
--        "release_date",
--        "duration",
--        "rating_id"
--    )
--VALUES
--    (
--        1,
--        'Название фильма',
--        'Описание фильма',
--        '2023-10-09',
--        120,
--        1
--    ),
--    (
--        2,
--        'Другой фильм',
--        'Описание другого фильма',
--        '2023-10-10',
--        150,
--        2
--    );
--
--INSERT INTO
--    "User" ("email", "login", "name", "birthday")
--VALUES
--    (
--        'user1@example.com',
--        'user1',
--        'Иван',
--        '1990-01-01'
--    ),
--    (
--        'user2@example.com',
--        'user2',
--        'Мария',
--        '1995-02-15'
--    );
--
--INSERT INTO
--    "FilmGenre" ("film_genre_id", "film_id", "genre_id")
--VALUES
--    ('1-1', 1, 1),
--    ('1-2', 1, 2),
--    ('2-2', 2, 2);
--
--INSERT INTO
--    "FilmLikes" ("film_user_id", "film_id", "user_id")
--VALUES
--    ('1-1-1', 1, 1),
--    ('1-1-2', 1, 2),
--    ('2-2-1', 2, 1);
--
--INSERT INTO
--    "Friends" (
--        "user_user_id",
--        "follower_id",
--        "followed_id",
--        "status_id"
--    )
--VALUES
--    ('1_2', 1, 2, 1);
