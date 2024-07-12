package com.example.zomnieapp.ui_app.data.mock;

import static com.example.zomnieapp.ui_app.MainFrame.dataRepository;

public class GameEngine {

    private final DataGenerator dataGenerator = new DataGenerator();

    public static int width = 40;
    public static int height = 100;

    public void startGame() {
        Thread mapThread = new Thread(() -> {
            while (true) {
                dataRepository.onNewMap(dataGenerator.generateMap(width, height));
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