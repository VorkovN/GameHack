package com.example.zomnieapp.commands;

import com.example.zomnieapp.app.HeaderConfig;
import com.example.zomnieapp.temp.JSON;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class World {

    private static final String URL = "https://games-test.datsteam.dev/play/zombidef/world";
    private final RestTemplate restTemplate;

    public World() {
        this.restTemplate = new RestTemplate();
    }

    public void printWorld() {
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(HeaderConfig.getAuthHeader());
//            ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, String.class);

//            String responseBody = responseEntity.getBody();
            var json = new JSON();
            String responseBody = json.getMap();
            System.out.println(formatWorld(responseBody));
        } catch (Exception e) {
            System.out.println("An error occurred at World class: " + e.getMessage());
        }
    }

    private String formatWorld(String responseBody) {
        // Простой парсер JSON для консольного вывода
        StringBuilder formattedWorld = new StringBuilder();
        formattedWorld.append("World Data:\n");
        formattedWorld.append(responseBody.replace(",", ",\n"));
        return formattedWorld.toString();
    }
}

