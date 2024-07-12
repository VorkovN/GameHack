package com.example.zomnieapp.ui_app.data.model;

import com.example.zomnieapp.ui_app.data.model.base.BaseSubject;
import com.example.zomnieapp.ui_app.data.model.enemy.EnemyBaseSubject;
import com.example.zomnieapp.ui_app.data.model.zombie.ZombieSubject;

import java.awt.*;

import static com.example.zomnieapp.ui_app.data.model.MapPointType.WALL;

public class RenderMapPoint {

    private MapPointType type;

    private MapSubject subject;

    public RenderMapPoint(MapPointType type, MapSubject subject) {
        this.type = type;
        this.subject = subject;
    }

    public Color getColor() {
        if (type == WALL) {
            return Color.DARK_GRAY;
        } else if (subject instanceof EnemyBaseSubject) {
            return Color.RED;
        } else if (subject instanceof BaseSubject) {
            return Color.BLUE;
        } else if (subject instanceof ZombieSubject) {
            return Color.GREEN;
        }
        else {
            return Color.WHITE;
        }
    }

    @Override
    public String toString() {
        return "RenderMapPoint{" +
                "type=" + type +
                ", subject=" + subject +
                '}';
    }
}
