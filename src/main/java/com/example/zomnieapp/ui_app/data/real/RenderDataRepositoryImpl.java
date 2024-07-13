package com.example.zomnieapp.ui_app.data.real;

import com.example.zomnieapp.ui_app.data.RenderDataRepository;
import com.example.zomnieapp.ui_app.data.model.Coordinate;
import com.example.zomnieapp.ui_app.data.model.PlayerStatus;
import com.example.zomnieapp.ui_app.data.model.VisibleMapPoint;
import com.example.zomnieapp.ui_app.ui.MainFrame;
import com.example.zomnieapp.ui_app.ui.model.RenderMapPoint;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.zomnieapp.ui_app.data.model.MapPointType.DEFAULT;
import static com.example.zomnieapp.ui_app.data.model.MapPointType.FREE;
import static java.lang.Math.max;

//@Component
public class RenderDataRepositoryImpl implements RenderDataRepository {

    private volatile OnNewDataListener onNewDataListener;
    private static final int GRID_SIZE = 40;

    @Override
    public void listenToNewData(OnNewDataListener onNewDataListener) {
        this.onNewDataListener = onNewDataListener;
    }

    @Override
    public void onNewMap(int fullMapWidth, int fullMapHeight, List<VisibleMapPoint> points) {
        tryEmitNewMap(convertToRenderMap(fullMapWidth, fullMapHeight, points));
    }

    @Override
    public void onNewPlayerStatus(PlayerStatus playerStatus) {
        tryEmitNewPlayerStatus(playerStatus);
    }

    private void tryEmitNewMap(List<List<RenderMapPoint>> map) {
        try {
            onNewDataListener.newMap(map);
        } catch (Throwable t) {
            System.out.println("error in new map : " + t.getMessage());
        }
    }

    public static List<List<RenderMapPoint>> convertToRenderMap(int fullMapWidth, int fullMapHeight, List<VisibleMapPoint> visibleMapPoints) {

        // Найти минимальные и максимальные координаты для смещения
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (VisibleMapPoint vmp : visibleMapPoints) {
            Coordinate coord = vmp.getCoordinate();
            minX = Math.min(minX, coord.getX());
            maxX = max(maxX, coord.getX());
            minY = Math.min(minY, coord.getY());
            maxY = max(maxY, coord.getY());
        }
        int gridSize = max(GRID_SIZE, max(maxX - minY, maxY - minY));
        List<List<RenderMapPoint>> renderMap = new ArrayList<>();

        // Инициализация пустой карты
        for (int i = 0; i < gridSize; i++) {
            List<RenderMapPoint> row = new ArrayList<>(gridSize);
            for (int j = 0; j < gridSize; j++) {
                row.add(new RenderMapPoint(FREE, null, false));  // Заполняем null, чтобы позже заменить на реальные точки
            }
            renderMap.add(row);
        }

        // Вычисление смещения для центрирования видимых точек
        int rangeX = maxX - minX + 1;
        int rangeY = maxY - minY + 1;

        int offsetX = (gridSize - rangeX) / 2 - minX;
        int offsetY = (gridSize - rangeY) / 2 - minY;

        // Заполнение карты данными из visibleMapPoints с учётом смещения
        for (VisibleMapPoint vmp : visibleMapPoints) {
            Coordinate coord = vmp.getCoordinate();
            int adjustedX = coord.getX() + offsetX;
            int adjustedY = coord.getY() + offsetY;
            if (adjustedX >= 0 && adjustedX < gridSize && adjustedY >= 0 && adjustedY < gridSize) {
                renderMap.get(adjustedX).set(adjustedY, new RenderMapPoint(vmp.getType(), vmp.getSubject(), true));
            }
        }

        return renderMap;
    }


    private void tryEmitNewPlayerStatus(PlayerStatus playerStatus) {
        try {
            onNewDataListener.newPlayer(playerStatus);
        } catch (Throwable t) {
            System.out.println("error in player : " + t.getMessage());
        }
    }
}

