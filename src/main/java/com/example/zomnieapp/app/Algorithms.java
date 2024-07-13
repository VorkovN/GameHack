package com.example.zomnieapp.app;

import com.example.zomnieapp.body.Attack;
import com.example.zomnieapp.body.BodyCommand;
import com.example.zomnieapp.body.Build;
import com.example.zomnieapp.body.Target;
import com.example.zomnieapp.units.*;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.awt.geom.Point2D.distance;


public class Algorithms {

    public static final int WIDTH_MAP = 100;

    public static ArrayList<Cell> buildMap(List<Zombie> zombieList, List<Base> basesList, List<EnemyBlock> enemyBlocksList, List<Zpot> zpotList, Point centerPoint) {

        ArrayList<Cell> cells = generateGrid(centerPoint); // Генерим свою пустую карту вокруг центральной точки

        // Вписываем в нашу локальную карту зомбаков
        for (Zombie zombie : zombieList) {
            for (Cell cell: cells) {
                if (zombie.getX() == cell.getX() && zombie.getY() == cell.getY()) {
                    cell.setType("zombie");
                    break;
                }
            }
        }

        // Вписываем в нашу локальную карту базы
        for (Base base : basesList) {
            for (Cell cell: cells) {
                if (base.getX() == cell.getX() && base.getY() == cell.getY()) {
                    cell.setType("base");
                    break;
                }
            }
        }

        // Вписываем в нашу локальную карту базы противника
        for (EnemyBlock enemyBlock : enemyBlocksList) {
            for (Cell cell: cells) {
                if (enemyBlock.getX() == cell.getX() && enemyBlock.getY() == cell.getY()) {
                    cell.setType("enemyBlock");
                    break;
                }
            }
        }

        // Вписываем в нашу локальную карту базы противника
        for (Zpot zpot : zpotList) {
            for (Cell cell: cells) {
                if (zpot.getX() == cell.getX() && zpot.getY() == cell.getY()) {
                    cell.setType(zpot.getType());
                    break;
                }
            }
        }

        return cells;
    }


    public static BodyCommand generateCommand(List<Zombie> zombieList, List<Base> basesList, List<EnemyBlock> enemyBlocksList, List<Cell> cells, Point centerPoint, int coins) {
        zombieList.sort(Comparator.comparingDouble(zombie -> distance(zombie.getX(), zombie.getY(), centerPoint.getX(), centerPoint.getY())));
        basesList.sort(Comparator.comparingDouble(base -> distance(base.getX(), base.getY(), centerPoint.getX(), centerPoint.getY())));
        enemyBlocksList.sort(Comparator.comparingDouble(enemyBlock -> distance(enemyBlock.getX(), enemyBlock.getY(), centerPoint.getX(), centerPoint.getY())));
        cells.sort(Comparator.comparingDouble(cell -> distance(cell.getX(), cell.getY(), centerPoint.getX(), centerPoint.getY())));

        System.out.println("zombieList.size: " + zombieList.size());
        System.out.println("basesList.size: " + basesList.size());
        System.out.println("enemyBlocksList.size: " + enemyBlocksList.size());

        int zombie_counter = 0;
        int enemy_counter = 0;
        List<Attack> attacks = new ArrayList<>();
        Iterator<Base> baseIterator = basesList.iterator();

        // Распределяем атаку баз по зомби
        for (Zombie zombie : zombieList) {
            if (!isZombieComingToUs(centerPoint, zombie)) {
                continue;
            }

            while (baseIterator.hasNext()) {
                Base base = baseIterator.next();
                if (distance(base.getX(), base.getY(), zombie.getX(), zombie.getY()) > base.getRange()) {
                    continue;
                }

                attacks.add(new Attack(base.getId(), new Target(zombie.getX(), zombie.getY())));
                ++zombie_counter;
                baseIterator.remove();
                if (zombie.getHealth() <= 0) {
                    break;
                }
            }
        }
        System.out.println("zombie_counter: " + zombie_counter);

        // Распределяем атаку баз по вражеским клеткам
        for (EnemyBlock enemyBlocks : enemyBlocksList) {
            while (baseIterator.hasNext()) {
                Base base = baseIterator.next();
                if (distance(base.getX(), base.getY(), enemyBlocks.getX(), enemyBlocks.getY()) > base.getRange()) {
                    continue;
                }

                attacks.add(new Attack(base.getId(), new Target(enemyBlocks.getX(), enemyBlocks.getY())));
                ++enemy_counter;
                baseIterator.remove();
                if (enemyBlocks.getHealth() <= 0) {
                    break;
                }
            }
        }
        System.out.println("enemy_counter: " + enemy_counter);


        List<Build> builds = new ArrayList<>();
        Iterator<Cell> cellIterator = cells.iterator();
        while (coins > 0 && cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getType().equals("free")) {
                --coins;
                builds.add(new Build(cell.getX(), cell.getY()));
            }

        }

        return new BodyCommand(attacks, builds, null);
    }

    public static ArrayList<Cell> generateGrid(Point point) {
        int grid_size = WIDTH_MAP / 2; // Половина размера сетки
        ArrayList<Cell> grid = new ArrayList<>();
        for (int x = point.x - grid_size; x < point.x + grid_size; x++) {
            for (int y = point.y - grid_size; y < point.y + grid_size; y++) {
                grid.add(new Cell("free", x, y));
            }
        }

        return grid;
    }

    // Определяем на нас ли движется зомби, если от нас, то и хуй с ним
    public static boolean isZombieComingToUs(Point centerPoint, Zombie zombie) {
        return centerPoint.getX() - zombie.getX() > 0 && zombie.getDirection().equals("right") ||
               centerPoint.getX() - zombie.getX() < 0 && zombie.getDirection().equals("left") ||
               centerPoint.getY() - zombie.getY() > 0 && zombie.getDirection().equals("up") ||
               centerPoint.getX() - zombie.getX() > 0 && zombie.getDirection().equals("down");
    }

}
