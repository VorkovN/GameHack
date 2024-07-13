package com.example.zomnieapp.units;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnemyBlock {
    private int health;
    private int x;
    private int y;

    private int attack;

    private String name;

    private boolean isHead;
}