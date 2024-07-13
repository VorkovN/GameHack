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

    public static TreeMap<Point, Cell> buildMap(List<Zombie> zombieList, List<Base> basesList, List<EnemyBlock> enemyBlocksList, List<Zpot> zpotList, Point centerPoint) {

        TreeMap<Point, Cell> cells = generateGrid(centerPoint); // Генерим свою пустую карту вокруг центральной точки

        // Вписываем в нашу локальную карту зомбаков
        for (Zombie zombie : zombieList) {
            Cell cell = cells.get(new Point(zombie.getX(), zombie.getY()));
            if (cell != null) {
                cell.setType("zombie");
            }
        }

        // Вписываем в нашу локальную карту базы
        for (Base base : basesList) {
            Cell cell = cells.get(new Point(base.getX(), base.getY()));
            if (cell != null) {
                cell.setType("base");
            }
        }

        // Вписываем в нашу локальную карту базы противника
        for (EnemyBlock enemyBlock : enemyBlocksList) {
            Cell cell = cells.get(new Point(enemyBlock.getX(), enemyBlock.getY()));
            if (cell != null) {
                cell.setType("enemyBlock");

                Cell cell_up = cells.get(new Point(enemyBlock.getX(), enemyBlock.getY()+1));
                if (cell_up != null && Objects.equals(cell_up.getType(), "free")) {
                    cell_up.setType("no_build");
                }

                Cell cell_down = cells.get(new Point(enemyBlock.getX(), enemyBlock.getY()-1));
                if (cell_down != null && Objects.equals(cell_down.getType(), "free")) {
                    cell_down.setType("no_build");
                }

                Cell cell_right = cells.get(new Point(enemyBlock.getX()+1, enemyBlock.getY()));
                if (cell_right != null && Objects.equals(cell_right.getType(), "free")) {
                    cell_right.setType("no_build");
                }

                Cell cell_left = cells.get(new Point(enemyBlock.getX()-1, enemyBlock.getY()));
                if (cell_left != null && Objects.equals(cell_left.getType(), "free")) {
                    cell_left.setType("no_build");
                }
            }
        }

        // Вписываем в нашу локальную карту базы противника
        for (Zpot zpot : zpotList) {
            Cell cell = cells.get(new Point(zpot.getX(), zpot.getY()));
            if (cell != null) {
                cell.setType(zpot.getType());

                if (Objects.equals(zpot.getType(), "default")) {
                    Cell cell_up = cells.get(new Point(zpot.getX(), zpot.getY()+1));
                    if (cell_up != null && Objects.equals(cell_up.getType(), "free")) {
                        cell_up.setType("no_build");
                    }

                    Cell cell_down = cells.get(new Point(zpot.getX(), zpot.getY()-1));
                    if (cell_down != null && Objects.equals(cell_down.getType(), "free")) {
                        cell_down.setType("no_build");
                    }

                    Cell cell_right = cells.get(new Point(zpot.getX()+1, zpot.getY()));
                    if (cell_right != null && Objects.equals(cell_right.getType(), "free")) {
                        cell_right.setType("no_build");
                    }

                    Cell cell_left = cells.get(new Point(zpot.getX()-1, zpot.getY()));
                    if (cell_left != null && Objects.equals(cell_left.getType(), "free")) {
                        cell_left.setType("no_build");
                    }
                }
            }
        }

        return cells;
    }


    public static BodyCommand generateCommand(List<Zombie> zombieList, List<Base> basesList, List<EnemyBlock> enemyBlocksList, TreeMap<Point, Cell> cells, Point centerPoint, int coins) {
        zombieList.sort(Comparator.comparingDouble(zombie -> distance(zombie.getX(), zombie.getY(), centerPoint.getX(), centerPoint.getY())));
        basesList.sort(Comparator.comparingDouble(base -> distance(base.getX(), base.getY(), centerPoint.getX(), centerPoint.getY())));
        enemyBlocksList.sort(Comparator.comparingDouble(enemyBlock -> distance(enemyBlock.getX(), enemyBlock.getY(), centerPoint.getX(), centerPoint.getY())));
//        cells.sort(Comparator.comparingDouble(cell -> distance(cell.getX(), cell.getY(), centerPoint.getX(), centerPoint.getY())));

        System.out.println("zombieList.size: " + zombieList.size());
        System.out.println("basesList.size: " + basesList.size());
        System.out.println("enemyBlocksList.size: " + enemyBlocksList.size());

        int zombie_counter = 0;
        int enemy_counter = 0;
        List<Attack> attacks = new ArrayList<>();
        Iterator<Base> baseIterator = basesList.iterator();

        // Распределяем атаку баз по зомби
        for (Zombie zombie : zombieList) {
//            if (!isZombieComingToUs(centerPoint, zombie)) {
//                continue;
//            }

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
        for (Map.Entry<Point, Cell> entry : cells.entrySet()) {
            if (coins <= 0) {
                break;
            }

            if (entry.getValue().getType().equals("free")) {
                builds.add(new Build(entry.getValue().getX(), entry.getValue().getY()));
                --coins;
            }
        }

        return new BodyCommand(attacks, builds, null);
    }

    public static TreeMap<Point, Cell> generateGrid(Point point) {
        int grid_size = WIDTH_MAP / 2; // Половина размера сетки

        Comparator<Point> comparator = new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                double distance1 = p1.distance(0, 0);
                double distance2 = p2.distance(0, 0);

                return Double.compare(distance1, distance2);
            }
        };

        TreeMap<Point, Cell> grid = new TreeMap<>(comparator);
        for (int x = point.x - grid_size; x < point.x + grid_size; x++) {
            for (int y = point.y - grid_size; y < point.y + grid_size; y++) {
                grid.put(new Point(x, y), new Cell("free", x, y));
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
