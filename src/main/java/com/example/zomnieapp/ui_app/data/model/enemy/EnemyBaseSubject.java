package com.example.zomnieapp.ui_app.data.model.enemy;

import com.example.zomnieapp.ui_app.data.model.Coordinate;
import com.example.zomnieapp.ui_app.data.model.base.BaseSubject;

public class EnemyBaseSubject extends BaseSubject {

    public EnemyBaseSubject(int attack, int health, boolean isHead, Coordinate lastAttack, String name, int x, int y) {
        super(attack, health, isHead, lastAttack, name, x, y);
    }
}
