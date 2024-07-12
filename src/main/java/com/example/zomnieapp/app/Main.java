package com.example.zomnieapp.app;


import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Main {

    private final Registration registration;

    @Scheduled(cron = "*/1 * * * * *")
    public void mainTask(){
        var isSuccessfulReg = registration.registration();
        if (isSuccessfulReg) {

        }
    }
}
