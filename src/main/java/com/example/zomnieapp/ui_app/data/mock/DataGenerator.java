package com.example.zomnieapp.ui_app.data.mock;

import com.example.zomnieapp.ui_app.data.mock.model.MockMapPoint;
import com.example.zomnieapp.ui_app.data.model.Coordinate;
import com.example.zomnieapp.ui_app.data.model.PlayerStatus;
import com.example.zomnieapp.ui_app.data.model.VisibleMapPoint;

import java.time.LocalDateTime;
import java.util.*;

public class DataGenerator {

    private static final Random random = new Random();

    private final MockMapPointGenerator mockMapPointGenerator = new MockMapPointGenerator();


    private volatile List<List<MockMapPoint>> fullMap;

    private int width;

    private int height;

    public DataGenerator(int width, int height) {
        fullMap = generateFullMap(width, height);
        this.width = width;
        this.height = height;
    }

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

    public List<List<MockMapPoint>> generateFullMap(int width, int height) {
        ArrayList<List<MockMapPoint>> result = new ArrayList<>(width);
        for (int i = 0; i < width; i++) {
            ArrayList<MockMapPoint> line = new ArrayList<>(height);
            for (int j = 0; j < height; j++) {
                line.add(mockMapPointGenerator.generateMockMapPoint(new Coordinate(i, j)));
            }
            result.add(line);
        }
        return result;
    }

    public List<VisibleMapPoint> generateMap(int width, int height, Set<Coordinate> coordinates, int changes) {
        ArrayList<VisibleMapPoint> result = new ArrayList<>();
        modifyRandomPoints(changes);
        Set<Coordinate> coordinatesSet = new HashSet<>(coordinates);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Coordinate currentCoord = new Coordinate(i, j);
                if (coordinatesSet.contains(currentCoord)) {
                    MockMapPoint mockMapPoint = fullMap.get(i).get(j);
                    result.add(new VisibleMapPoint(mockMapPoint.getType(), mockMapPoint.getSubject(), new Coordinate(i, j)));
                }
            }
        }
        return result;
    }

    private void modifyRandomPoints(int n) {
        int width = fullMap.size();
        int height = fullMap.get(0).size();

        for (int i = 0; i < n; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            fullMap.get(x).set(y, mockMapPointGenerator.generateMockMapPoint(new Coordinate(x, y)));
        }
    }

    public Set<Coordinate> generateVisibleCoordinates(Set<Coordinate> currentCoordinates) {
        Set<Coordinate> allCoordinates = new HashSet<>(currentCoordinates);
        if (currentCoordinates.isEmpty()) {
            // Генерация 10 координат, вплотную стоящих друг к другу
            int startX = random.nextInt(fullMap.size() - 10); // Гарантирует, что будет место для 10 координат
            int startY = random.nextInt(fullMap.get(0).size() - 10);

            boolean vertical = random.nextBoolean(); // Определяет, будут ли координаты расположены вертикально или горизонтально

            for (int i = 0; i < 10; i++) {
                if (vertical) {
                    allCoordinates.add(new Coordinate(startX, startY + i));
                } else {
                    allCoordinates.add(new Coordinate(startX + i, startY));
                }
            }
        } else {
            Coordinate newCoordinate;

            do {
                newCoordinate = getRandomNeighbor(currentCoordinates);
            } while (currentCoordinates.contains(newCoordinate) || allCoordinates.contains(newCoordinate));

            allCoordinates.add(newCoordinate);
        }

        return allCoordinates;
    }

    private Coordinate getRandomNeighbor(Set<Coordinate> coordinates) {
        // Выбираем случайную координату из текущих
        Coordinate randomCurrent = coordinates.toArray(new Coordinate[0])[random.nextInt(coordinates.size())];

        // Список возможных смещений (вверх, вниз, влево, вправо)
        int[][] offsets = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int newX;
        int newY;
        int offsetNum = random.nextInt(offsets.length);
        newX = randomCurrent.getX() + offsets[offsetNum][0];
        newY = randomCurrent.getY() + offsets[offsetNum][1];

        return new Coordinate(newX, newY);
    }
}