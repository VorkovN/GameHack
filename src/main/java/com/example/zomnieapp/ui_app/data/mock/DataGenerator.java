package com.example.zomnieapp.ui_app.data.mock;

import com.example.zomnieapp.ui_app.data.model.Coordinate;
import com.example.zomnieapp.ui_app.data.model.PlayerStatus;
import com.example.zomnieapp.ui_app.data.model.RenderMapPoint;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    private final Random random = new Random();

    private final RenderMapPointGenerator renderMapPointGenerator = new RenderMapPointGenerator();

    public PlayerStatus generatePlayerStatus() {
        PlayerStatus playerStatus = new PlayerStatus(
            random.nextInt(100),
            LocalDateTime.now(),
            random.nextInt(1000),
            "test",
            random.nextInt(10000),
            random.nextInt(200)
        );

        return playerStatus;
    }

    public List<List<RenderMapPoint>> generateMap(int width, int height) {
        ArrayList<List<RenderMapPoint>> result = new ArrayList<>(width);
        for (int i = 0; i < width; i++) {
            ArrayList<RenderMapPoint> line = new ArrayList<>(height);
            for (int j = 0; j < height; j++) {
                line.add(renderMapPointGenerator.generateRenderMapPoint(new Coordinate(i, j)));
            }
            result.add(line);
        }
        return result;
    }
}