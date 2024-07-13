package com.example.zomnieapp.ui_app.data.model.base;

import com.example.zomnieapp.ui_app.data.model.Coordinate;
import com.example.zomnieapp.ui_app.data.model.MapSubject;

public class BaseSubject implements MapSubject {
    private final int attack;
    private final int health;
    private final boolean isHead;
    private final Coordinate lastAttack;
    private final String name;
    private final int x;
    private final int y;

    public BaseSubject(int attack, int health, boolean isHead, Coordinate lastAttack, String name, int x, int y) {
        this.attack = attack;
        this.health = health;
        this.isHead = isHead;
        this.lastAttack = lastAttack;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public int getAttack() {
        return attack;
    }

    public int getHealth() {
        return health;
    }

    public boolean isHead() {
        return isHead;
    }

    public Coordinate getLastAttack() {
        return lastAttack;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Base{" +
                "attack=" + attack +
                ", health=" + health +
                ", isHead=" + isHead +
                ", lastAttack=" + lastAttack +
                ", name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
