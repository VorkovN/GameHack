package com.example.zomnieapp.commands;

import com.example.zomnieapp.app.HeaderConfig;
import com.example.zomnieapp.body.BodyCommand;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Command {

    private static final String URL = "https://games.datsteam.dev/play/zombidef/command";
    private final RestTemplate restTemplate;
    private final Gson gson;

    public Command() {
        this.restTemplate = new RestTemplate();
        this.gson = new Gson();
    }

    public void execute(BodyCommand bodyCommand) {
        try {
            HttpHeaders headers = HeaderConfig.getAuthHeader();
            String gsonBody = gson.toJson(bodyCommand);
            System.out.println(gsonBody);
            HttpEntity<String> requestEntity = new HttpEntity<>(gsonBody, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class); //
            System.out.println(responseEntity.getBody());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
