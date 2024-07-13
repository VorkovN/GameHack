package com.example.zomnieapp.ui_app.data.mock;

import com.example.zomnieapp.ui_app.data.model.Coordinate;
import com.example.zomnieapp.ui_app.data.model.VisibleMapPoint;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.zomnieapp.ui_app.ui.MainFrame.dataRepository;

public class GameEngine {


    public static int width = 1000;
    public static int height = 1000;
    private final DataGenerator dataGenerator = new DataGenerator(width, height);

    public void startGame() {
        Thread mapThread = new Thread(() -> {
            Set<Coordinate> visibleCoordinates = new HashSet<>();
            while (true) {
                visibleCoordinates = dataGenerator.generateVisibleCoordinates(visibleCoordinates);
                List<VisibleMapPoint> visibleMapPoints = dataGenerator.generateMap(width, height, visibleCoordinates, 10);
                dataRepository.onNewMap(visibleMapPoints);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        mapThread.start();
        Thread playerStatus = new Thread(() -> {
            while (true) {
                dataRepository.onNewPlayerStatus(dataGenerator.generatePlayerStatus());
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        playerStatus.start();
    }
}