package com.example.zomnieapp.units;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Zombie {
    private int direction;
    private int health;
    private int x;
    private int y;
}