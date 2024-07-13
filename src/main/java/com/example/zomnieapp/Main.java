package com.example.zomnieapp;

import com.example.zomnieapp.adapter.LogicToGui;
import com.example.zomnieapp.app.Algorithms;
import com.example.zomnieapp.app.Registration;
import com.example.zomnieapp.body.BodyCommand;
import com.example.zomnieapp.commands.Command;
import com.example.zomnieapp.commands.UnitsService;
import com.example.zomnieapp.commands.WorldService;
import com.example.zomnieapp.ui_app.data.RenderDataRepository;
import com.example.zomnieapp.units.*;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class Main {

    private final Registration registration;
    private final WorldService worldService;
    private final UnitsService unitsService;
    private final Command command;
    private final LogicToGui logicToGui;

    @Scheduled(cron = "*/1 * * * * *")
    public void mainTask(){
//        var isSuccessfulReg = registration.registration();
//        if (!isSuccessfulReg) {
//            return;
//        }
        initServices();

        // основная логика
        int turnEndsInMs = unitsService.getTurnEndsInMs();
        List<Zpot> zpotList = worldService.getZpots();
        Player player = unitsService.getPlayer();
        List<Zombie> zombieList = unitsService.getZombies();
        List<Base> basesList = unitsService.getBases();
        List<EnemyBlock> enemyBlockList = unitsService.getEnemyBlocks();
        Base headBase = basesList.stream().filter(Base::isHead).findFirst().orElseThrow(() -> new RuntimeException("Head base not found"));
        Point centerPoint = new Point(headBase.getX(), headBase.getY());

        if (turnEndsInMs < 1000) {
            return;
        }

        System.out.println(centerPoint);
        System.out.println("turnEndsInMs: " + turnEndsInMs);
        System.out.println("EnemyBlockKills: " + player.getEnemyBlockKills());
        System.out.println("Gold: " + player.getGold());
        System.out.println("Points: " + player.getPoints());
        System.out.println("ZombieKills: " + player.getZombieKills());

        ArrayList<Cell> cells = Algorithms.buildMap(zombieList, basesList, enemyBlockList, zpotList, centerPoint);
//        logicToGui.execute(cells);
        BodyCommand bodyCommand = Algorithms.generateCommand(zombieList, basesList, enemyBlockList, cells, centerPoint, player.getGold());
        command.execute(bodyCommand);
    }

    private void initServices() {
        unitsService.getResponseAndInit();
        worldService.getResponseAndInit();
    }
}
