package com.example.zomnieapp.adapter;

import com.example.zomnieapp.app.Algorithms;
import com.example.zomnieapp.commands.UnitsService;
import com.example.zomnieapp.ui_app.data.RenderDataRepository;
import com.example.zomnieapp.ui_app.data.model.Coordinate;
import com.example.zomnieapp.ui_app.data.model.MapPointType;
import com.example.zomnieapp.ui_app.data.model.PlayerStatus;
import com.example.zomnieapp.ui_app.data.model.VisibleMapPoint;
import com.example.zomnieapp.ui_app.data.real.RenderDataRepositoryImpl;
import com.example.zomnieapp.ui_app.ui.MainFrame;
import com.example.zomnieapp.units.Cell;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
@AllArgsConstructor
public class LogicToGui {
    private final UnitsService unitsService;

    public void execute(TreeMap<Point, Cell> cells) {
        visualizeMap(cells);
        visualizePlayerStatus();
    }

    private void visualizePlayerStatus() {
        var player = unitsService.getPlayer();
        var visualPlayer = new PlayerStatus(
                player.getEnemyBlockKills(),
                String.format("time: %s", unitsService.getTurnEndsInMs()),
                player.getGold(),
                "Nikitos",
                player.getPoints(),
                player.getZombieKills());
        MainFrame.dataRepository.onNewPlayerStatus(visualPlayer);
    }

    private void visualizeMap(TreeMap<Point, Cell> cells) {
        List<VisibleMapPoint> visibleMapPoints = new ArrayList<>();
        for (Map.Entry<Point, Cell> entry : cells.entrySet()) {
            var vis = new VisibleMapPoint(
                    convertToMapPointType(entry.getValue().getType()),
                    entry.getValue().getMapSubject(),
                    new Coordinate(entry.getValue().getX(), entry.getValue().getY())
            );
            visibleMapPoints.add(vis);
        }
        MainFrame.dataRepository.onNewMap(Algorithms.WIDTH_MAP, Algorithms.WIDTH_MAP, visibleMapPoints);
    }

    private MapPointType convertToMapPointType(String cellType) {
        // "zombie", "wall", "default", "base", "enemyBlock", "free"
        switch (cellType) {
            case "default" : return MapPointType.DEFAULT;
            case "wall" : return MapPointType.WALL;
            case "zombie" : return MapPointType.ZOMBIE;
            case "base" : return MapPointType.BASE;
            case "enemyBlock" : return MapPointType.ENEMY_BLOCK;
            case "free" : return MapPointType.FREE;
            case "no_build" : return MapPointType.NO_BUILD;
            default: throw new RuntimeException("Unknown cell type for visual: " + cellType);
        }
    }
}
