package com.example.zomnieapp.commands;

import com.example.zomnieapp.app.HeaderConfig;
import com.example.zomnieapp.temp.JSON;
import org.springframework.http.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static com.example.zomnieapp.ui_app.MainFrame.dataRepository;

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
            String data = formatWorld(responseBody);
            System.out.println(data);
            dataRepository.newTexts(Collections.singletonList(data));
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

