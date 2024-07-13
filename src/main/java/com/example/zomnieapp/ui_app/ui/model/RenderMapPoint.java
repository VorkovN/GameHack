package com.example.zomnieapp.ui_app.ui.model;

import com.example.zomnieapp.ui_app.data.model.MapPointType;
import com.example.zomnieapp.ui_app.data.model.MapSubject;
import com.example.zomnieapp.ui_app.data.model.base.BaseSubject;
import com.example.zomnieapp.ui_app.data.model.enemy.EnemyBaseSubject;
import com.example.zomnieapp.ui_app.data.model.zombie.ZombieSubject;

import java.awt.*;

import static com.example.zomnieapp.ui_app.data.model.MapPointType.WALL;

public class RenderMapPoint {

    private MapPointType type;

    private MapSubject subject;

    private boolean visible;

    public RenderMapPoint(MapPointType type, MapSubject subject, boolean visible) {
        this.type = type;
        this.subject = subject;
        this.visible = visible;
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
        } else if (visible) {
            return Color.LIGHT_GRAY;
        } else {
            return Color.WHITE;
        }
    }

    @Override
    public String toString() {
        return "RenderMapPoint{" +
                "type=" + type +
                ", subject=" + subject +
                ", visible=" + visible +
                '}';
    }

    public MapPointType getType() {
        return type;
    }

    public MapSubject getSubject() {
        return subject;
    }

    public boolean isVisible() {
        return visible;
    }
}
