package com.example.zomnieapp;

import com.example.zomnieapp.app.Registration;
import com.example.zomnieapp.commands.World;
import com.example.zomnieapp.commands.Attack;
import com.example.zomnieapp.commands.UnitsService;
import com.example.zomnieapp.units.Base;
import com.example.zomnieapp.units.Zombies;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class Main {

    private final Registration registration;
    private final UnitsService unitsService;
    private final Attack attack;

    @Scheduled(cron = "*/1 * * * * *")
    public void mainTask(){
        var isSuccessfulReg = registration.registration();
        if (isSuccessfulReg) {
            List<Zombies> zombiesList = unitsService.getZombies();

            if (zombiesList.isEmpty()) return;
            var firstZombie = zombiesList.get(0);

            List<Base> basesList = unitsService.getBases();

            for (var base : basesList) {
                attack.attackFromBase(base, firstZombie); // передается ID база и Zombie id x id y
            }
        }
    }
}
