package com.example.zomnieapp.units;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class EnemyBlock {
    private int health;
    private int x;
    private int y;
}