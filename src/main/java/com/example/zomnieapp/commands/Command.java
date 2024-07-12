package com.example.zomnieapp.commands;

import com.example.zomnieapp.app.HeaderConfig;
import com.example.zomnieapp.body.Attack;
import com.example.zomnieapp.body.BodyCommand;
import com.example.zomnieapp.body.Target;
import com.example.zomnieapp.units.Base;
import com.example.zomnieapp.units.Zombie;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class Command {

    private static final String URL = "https://games-test.datsteam.dev/play/zombidef/command";
    private final RestTemplate restTemplate;
    private final Gson gson;

    public Command() {
        this.restTemplate = new RestTemplate();
        this.gson = new Gson();
    }

    public void attackFromBases(List<Base> bases, Zombie zombie) {
        try {
            HttpHeaders headers = HeaderConfig.getAuthHeader();
            List<Attack> attacks = new ArrayList<>();
            for (var base : bases) {
                attacks.add(new Attack(base.getId(), new Target(zombie.getX(), zombie.getY())));
            }
            BodyCommand bodyCommand = new BodyCommand(attacks, null, null);
            String gsonBody = gson.toJson(bodyCommand);
            System.out.println(gsonBody);
            HttpEntity<String> requestEntity = new HttpEntity<>(gsonBody, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class); //
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
