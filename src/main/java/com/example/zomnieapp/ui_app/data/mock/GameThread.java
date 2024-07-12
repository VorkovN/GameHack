package com.example.zomnieapp.ui_app.data.mock;

public class GameThread extends Thread {
    @Override
    public void run() {

        GameEngine game = new GameEngine();
        game.startGame();
    }
}
