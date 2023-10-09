package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Friends {
    private  final  String id;
    private  final  Integer follower;
    private  final  Integer followed;
    private  final  String status;
}
