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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class LogicToGui {
    private final UnitsService unitsService;

    public void execute(ArrayList<Cell> cells) {
        visualizeMap(cells);
        visualizePlayerStatus();
    }

    private void visualizePlayerStatus() {
        var originPlayer = unitsService.getPlayer();
        var visualPlayer = new PlayerStatus(0, LocalDateTime.now(), originPlayer.getGold(), "name", 0, 0);
        MainFrame.dataRepository.onNewPlayerStatus(visualPlayer);
    }

    private void visualizeMap(ArrayList<Cell> cells) {
        List<VisibleMapPoint> visibleMapPoints = new ArrayList<>();
        for (Cell cell : cells) {
            var vis = new VisibleMapPoint(
                    convertToMapPointType(cell.getType()),
                    null,
                    new Coordinate(cell.getX(), cell.getY())
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
