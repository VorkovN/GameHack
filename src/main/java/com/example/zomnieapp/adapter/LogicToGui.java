package com.example.zomnieapp.adapter;

import com.example.zomnieapp.app.Algorithms;
import com.example.zomnieapp.commands.UnitsService;
import com.example.zomnieapp.ui_app.data.RenderDataRepository;
import com.example.zomnieapp.ui_app.data.model.Coordinate;
import com.example.zomnieapp.ui_app.data.model.MapPointType;
import com.example.zomnieapp.ui_app.data.model.PlayerStatus;
import com.example.zomnieapp.ui_app.data.model.VisibleMapPoint;
import com.example.zomnieapp.units.Cell;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class LogicToGui {
    private final RenderDataRepository renderDataRepository;
    private final UnitsService unitsService;

    public void execute(ArrayList<Cell> cells) {
        visualizeMap(cells);
        visualizePlayerStatus();
    }

    private void visualizePlayerStatus() {
        var originPlayer = unitsService.getPlayer();
        var visualPlayer = new PlayerStatus(0, LocalDateTime.now(), originPlayer.getGold(), "name", 0, 0);
        renderDataRepository.onNewPlayerStatus(visualPlayer);
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
        renderDataRepository.onNewMap(Algorithms.WIDTH_MAP, Algorithms.WIDTH_MAP, visibleMapPoints);
    }

    private MapPointType convertToMapPointType(String cellType) {
        // "zombie", "wall", "default", "base", "enemyBlock", "free"
        switch (cellType) {
            case "default" : return MapPointType.DEFAULT;
            case "wall" : return MapPointType.WALL;
            default: throw new RuntimeException("Unknown cell type for visual: " + cellType);
        }
    }
}
