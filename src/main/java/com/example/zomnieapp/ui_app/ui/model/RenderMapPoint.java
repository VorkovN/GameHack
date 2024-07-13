package com.example.zomnieapp.ui_app.ui.model;

import com.example.zomnieapp.ui_app.data.model.MapPointType;
import com.example.zomnieapp.ui_app.data.model.MapSubject;
import com.example.zomnieapp.ui_app.data.model.base.BaseSubject;
import com.example.zomnieapp.ui_app.data.model.enemy.EnemyBaseSubject;
import com.example.zomnieapp.ui_app.data.model.zombie.ZombieSubject;

import java.awt.*;
import java.util.Objects;

import static com.example.zomnieapp.ui_app.data.model.MapPointType.*;

public class RenderMapPoint {

    private final MapPointType type;

    private final MapSubject subject;

    private final boolean visible;

    public RenderMapPoint(MapPointType type, MapSubject subject, boolean visible) {
        this.type = type;
        this.subject = subject;
        this.visible = visible;
    }

    public Color getColor() {
        if (type == WALL) {
            return Color.DARK_GRAY;
        } else if (subject instanceof EnemyBaseSubject || type == ENEMY_BLOCK) {
            return Color.decode("#FF99BB"); // PINK
        } else if (subject instanceof BaseSubject || type == BASE) {
            return Color.decode("#66CCFF"); // BLUE
        } else if (subject instanceof ZombieSubject || type == ZOMBIE) {
            return Color.decode("#1EB300"); // GREEN
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RenderMapPoint that = (RenderMapPoint) o;
        return visible == that.visible && type == that.type && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, subject, visible);
    }
}
