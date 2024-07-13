package com.example.zomnieapp.units;

import com.example.zomnieapp.ui_app.data.model.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Base {
    private String id;
    private boolean isHead;
    private int range;
    private int x;
    private int y;

    private int attack;

    private int health;

    private String name;

    private Coordinate lastAttack;
}