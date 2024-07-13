package com.example.zomnieapp;

import com.example.zomnieapp.app.Algorithms;
import com.example.zomnieapp.app.Registration;
import com.example.zomnieapp.body.BodyCommand;
import com.example.zomnieapp.commands.Command;
import com.example.zomnieapp.commands.UnitsService;
import com.example.zomnieapp.commands.World;
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
    private final World world;
    private final UnitsService unitsService;
    private final Command command;

    @Scheduled(cron = "*/1 * * * * *")
    public void mainTask(){
        var isSuccessfulReg = registration.registration();
        if (isSuccessfulReg) { // Если регистрация успешная, значит игра еще не началась.
            return;
        }

        unitsService.getResponseAndInit();
        List<Zpot> zpotList = world.getZpots();
        Player player = unitsService.getPlayer();
        List<Zombie> zombieList = unitsService.getZombies();
        List<Base> basesList = unitsService.getBases();
        List<EnemyBlock> enemyBlockList = unitsService.getEnemyBlocks();
        Base headBase = basesList.stream().filter(Base::isHead).findFirst().orElseThrow(() -> new RuntimeException("Head base not found"));
        Point centerPoint = new Point(headBase.getX(), headBase.getY());

        ArrayList<Cell> cells = Algorithms.buildMap(zombieList, basesList, enemyBlockList, zpotList, centerPoint);
        //todo Отправить ване cells

        BodyCommand bodyCommand = Algorithms.generateCommand(zombieList, basesList, cells, centerPoint, player.getGold());

        command.attackFromBases(bodyCommand);
    }
}
