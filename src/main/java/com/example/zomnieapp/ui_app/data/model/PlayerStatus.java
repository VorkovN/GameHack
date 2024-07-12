package com.example.zomnieapp.ui_app.data.model;

import java.time.LocalDateTime;

public class PlayerStatus {
    private final int enemyBlockKills;
    private final LocalDateTime gameEndedAt;
    private final int gold;
    private final String name;
    private final int points;
    private final int zombieKills;

    public PlayerStatus(int enemyBlockKills, LocalDateTime gameEndedAt, int gold, String name, int points, int zombieKills) {
        this.enemyBlockKills = enemyBlockKills;
        this.gameEndedAt = gameEndedAt;
        this.gold = gold;
        this.name = name;
        this.points = points;
        this.zombieKills = zombieKills;
    }

    public int getEnemyBlockKills() {
        return enemyBlockKills;
    }

    public LocalDateTime getGameEndedAt() {
        return gameEndedAt;
    }

    public int getGold() {
        return gold;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getZombieKills() {
        return zombieKills;
    }

    @Override
    public String toString() {
        return "PlayerResponse{" +
                "enemyBlockKills=" + enemyBlockKills +
                ", gameEndedAt=" + gameEndedAt +
                ", gold=" + gold +
                ", name='" + name + '\'' +
                ", points=" + points +
                ", zombieKills=" + zombieKills +
                '}';
    }
}
