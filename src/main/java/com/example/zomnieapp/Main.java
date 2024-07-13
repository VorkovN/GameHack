package com.example.zomnieapp;

import com.example.zomnieapp.app.Algorithm;
import com.example.zomnieapp.app.Registration;
import com.example.zomnieapp.body.BodyCommand;
import com.example.zomnieapp.commands.Command;
import com.example.zomnieapp.commands.UnitsService;
import com.example.zomnieapp.commands.World;
import com.example.zomnieapp.units.Base;
import com.example.zomnieapp.units.Cell;
import com.example.zomnieapp.units.EnemyBlock;
import com.example.zomnieapp.units.Zombie;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.awt.geom.Point2D.distance;

@Component
@AllArgsConstructor
public class Main {

    private final Registration registration;
    private final World world;
    private final UnitsService unitsService;
    private final Command command;

    @Scheduled(cron = "*/2 * * * * *")
    public void mainTask(){
        var isSuccessfulReg = registration.registration();
        if (isSuccessfulReg) { // Если регистрация успешная, значит игра еще не началась.
            return;
        }

        List<Zombie> zombieList = unitsService.getZombies();
        List<Base> basesList = unitsService.getBases();
        List<EnemyBlock> enemyBlockList = unitsService.getEnemyBlocks();
        Base headBase = basesList.stream().filter(Base::isHead).findFirst().orElse(null);
        Point centerPoint = new Point(headBase.getX(), headBase.getY());

        zombieList.sort(Comparator.comparingDouble(zombie -> distance(zombie.getX(), zombie.getY(), centerPoint.getX(), centerPoint.getY())));
        basesList.sort(Comparator.comparingDouble(base -> distance(base.getX(), base.getY(), centerPoint.getX(), centerPoint.getY())));

        ArrayList<Cell> cells = Algorithm.buildMap(zombieList, basesList, enemyBlockList, centerPoint);
        BodyCommand bodyCommand = Algorithm.generateCommand(zombieList, basesList, cells, centerPoint);

        command.attackFromBases(bodyCommand); // передается ID база и Zombie id x id y
    }
}
