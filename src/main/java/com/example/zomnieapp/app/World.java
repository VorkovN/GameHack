package com.example.zomnieapp.app;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Component
public class World {

    private static final String URL = "https://games-test.datsteam.dev/play/zombidef/world";
    private final RestTemplate restTemplate;

    public World() {
        this.restTemplate = new RestTemplate();
    }

    public void printWorld() {
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);
            String responseBody = responseEntity.getBody();
            System.out.println(formatWorld(responseBody));
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
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
