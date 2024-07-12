package com.example.zomnieapp.ui_app.data.mock;

import com.example.zomnieapp.ui_app.data.model.Coordinate;
import com.example.zomnieapp.ui_app.data.model.MapPointType;
import com.example.zomnieapp.ui_app.data.model.MapSubject;
import com.example.zomnieapp.ui_app.data.model.RenderMapPoint;
import com.example.zomnieapp.ui_app.data.model.base.BaseSubject;
import com.example.zomnieapp.ui_app.data.model.enemy.EnemyBaseSubject;
import com.example.zomnieapp.ui_app.data.model.zombie.ZombieDirection;
import com.example.zomnieapp.ui_app.data.model.zombie.ZombieSubject;
import com.example.zomnieapp.ui_app.data.model.zombie.ZombieType;

import java.util.Random;
import java.util.UUID;

public class RenderMapPointGenerator {

    private final Random random = new Random();

    public RenderMapPoint generateRenderMapPoint(Coordinate coordinate) {
        int rand = random.nextInt(100);
        MapPointType type;
        if (rand < 5) {
            type = MapPointType.WALL;
        } else {
            type = MapPointType.DEFAULT;
        }
        MapSubject subject = generateRandomSubject(coordinate);

        RenderMapPoint renderMapPoint = new RenderMapPoint(type, subject);
        return renderMapPoint;
    }

    private MapSubject generateRandomSubject(Coordinate coordinate) {
        int choice = random.nextInt(50);
        switch (choice) {
            case 0:
                return new EnemyBaseSubject(random.nextInt(100), random.nextInt(100), false, generateRandomCoordinate(), "another-user", coordinate.x, coordinate.y);
            case 1:
                return new BaseSubject(random.nextInt(100), random.nextInt(100), false, generateRandomCoordinate(), "another-user", coordinate.x, coordinate.y);
            case 2:
                return new ZombieSubject(random.nextInt(100), ZombieDirection.UP, 13, UUID.randomUUID(), 3, ZombieType.FAST, 5, coordinate.x, coordinate.y);
            default:
                return null;
        }
    }


    private Coordinate generateRandomCoordinate() {
        int x = random.nextInt(100); // Предполагаем, что размер карты 100x100
        int y = random.nextInt(100);
        return new Coordinate(x, y);
    }
}
