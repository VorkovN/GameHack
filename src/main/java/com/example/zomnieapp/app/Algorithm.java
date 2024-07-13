package com.example.zomnieapp.app;

import com.example.zomnieapp.body.Attack;
import com.example.zomnieapp.body.BodyCommand;
import com.example.zomnieapp.body.Build;
import com.example.zomnieapp.body.Target;
import com.example.zomnieapp.units.Base;
import com.example.zomnieapp.units.EnemyBlock;
import com.example.zomnieapp.units.Zombie;
import com.example.zomnieapp.units.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Iterator;

import static java.awt.geom.Point2D.distance;


public class Algorithm {

    public static ArrayList<Cell> buildMap(List<Zombie> zombieList, List<Base> basesList, List<EnemyBlock> enemyBlocksList, Point centerPoint) {

        ArrayList<Cell> cells = generateGrid(centerPoint);

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


        return cells;
    }


    public static BodyCommand generateCommand(List<Zombie> zombieList, List<Base> basesList, List<Cell> cells, Point centerPoint) {
        zombieList.sort(Comparator.comparingDouble(zombie -> distance(zombie.getX(), zombie.getY(), centerPoint.getX(), centerPoint.getY())));
        basesList.sort(Comparator.comparingDouble(base -> distance(base.getX(), base.getY(), centerPoint.getX(), centerPoint.getY())));
        cells.sort(Comparator.comparingDouble(cell -> distance(cell.getX(), cell.getY(), centerPoint.getX(), centerPoint.getY())));

        List<Attack> attacks = new ArrayList<>();
        { // Распределяем атаку баз по зомби
            Iterator<Zombie> zombieIterator = zombieList.iterator();
            Iterator<Base> baseIterator = basesList.iterator();
            Zombie zombie = zombieIterator.next();
            while (baseIterator.hasNext()) {
                Base base = baseIterator.next();
                attacks.add(new Attack(base.getId(), new Target(zombie.getX(), zombie.getY())));
                if (zombie.getHealth() <= 0) {
                    if (!zombieIterator.hasNext()) {
                        break;
                    }
                    zombie = zombieIterator.next();
                }
            }
        }


        int coins = 10;
        List<Build> builds = new ArrayList<>();
        Iterator<Cell> cellIterator = cells.iterator();
        Cell cell = cellIterator.next();
        while (coins > 0 && cellIterator.hasNext()) {
            if (cell.getType() == "free") {
                --coins;
                builds.add(new Build(cell.getX(), cell.getY()));
                cellIterator.next();
            }
        }

        return new BodyCommand(attacks, builds, null);
    }

    public static ArrayList<Cell> generateGrid(Point point) {
        int grid_size = 20; // Половина размера сетки
        ArrayList<Cell> grid = new ArrayList<>();
        for (int x = point.x - grid_size; x < point.x + grid_size; x++) {
            for (int y = point.x - grid_size; y < point.x + grid_size; y++) {
                grid.add(new Cell("free", x, y));
            }
        }

        return grid;
    }

}
