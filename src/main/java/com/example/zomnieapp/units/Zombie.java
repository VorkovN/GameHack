package com.example.zomnieapp.units;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Zombie {
    private String direction;
    private int health;
    private int x;
    private int y;
    private String type;
    private String id;
    private int attack;
    private int waitTurns;
    private int speed;
}