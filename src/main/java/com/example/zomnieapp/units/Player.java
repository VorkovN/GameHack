package com.example.zomnieapp.units;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Player {
    private int enemyBlockKills;
    private int gold;
    private int points;
    private int zombieKills;
}