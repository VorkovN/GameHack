package com.example.zomnieapp;

import com.example.zomnieapp.app.Registration;
import com.example.zomnieapp.commands.Command;
import com.example.zomnieapp.commands.UnitsService;
import com.example.zomnieapp.units.Base;
import com.example.zomnieapp.units.Zombie;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class Main {

    private final Registration registration;
    private final UnitsService unitsService;
    private final Command command;

    @Scheduled(cron = "*/1 * * * * *")
    public void mainTask(){
        var isSuccessfulReg = registration.registration();
        if (isSuccessfulReg) {
            List<Zombie> zombieList = unitsService.getZombies();

            if (zombieList.isEmpty()) return;
            var firstZombie = zombieList.get(0);

            List<Base> basesList = unitsService.getBases();

            command.attackFromBases(basesList, firstZombie); // передается ID база и Zombie id x id y
        }
    }
}
