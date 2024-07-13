package com.example.zomnieapp.ui_app.data.model.zombie;

import com.example.zomnieapp.ui_app.data.model.MapSubject;

import java.util.UUID;

public class ZombieSubject implements MapSubject {
    private final int attack;
    private final String direction;
    private final int health;
    private final String id;
    private final int speed;
    private final String type;
    private int waitTurns;
    private int x;
    private int y;

    public ZombieSubject(int attack, String direction, int health, String id, int speed, String type, int waitTurns, int x, int y) {
        this.attack = attack;
        this.direction = direction;
        this.health = health;
        this.id = id;
        this.speed = speed;
        this.type = type;
        this.waitTurns = waitTurns;
        this.x = x;
        this.y = y;
    }

    public int getAttack() {
        return attack;
    }

    public String getDirection() {
        return direction;
    }

    public int getHealth() {
        return health;
    }

    public String getId() {
        return id;
    }

    public int getSpeed() {
        return speed;
    }

    public String getType() {
        return type;
    }

    public int getWaitTurns() {
        return waitTurns;
    }

    public void setWaitTurns(int waitTurns) {
        this.waitTurns = waitTurns;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "ZombieResponse{" +
                "attack=" + attack +
                ", direction=" + direction +
                ", health=" + health +
                ", id=" + id +
                ", speed=" + speed +
                ", type=" + type +
                ", waitTurns=" + waitTurns +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
