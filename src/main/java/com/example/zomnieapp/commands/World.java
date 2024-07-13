package com.example.zomnieapp.commands;

import com.example.zomnieapp.app.HeaderConfig;
import com.example.zomnieapp.temp.JSON;
import com.example.zomnieapp.units.Zpot;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class World {

    private static final String URL = "https://games-test.datsteam.dev/play/zombidef/world";
    private final RestTemplate restTemplate;

    public World() {
        this.restTemplate = new RestTemplate();
    }

    public List<Zpot> getZpots() {
        List<Zpot> zpotList = new ArrayList<>();
        try {
            HttpHeaders headers = HeaderConfig.getAuthHeader();
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, String.class); //
            String responseBody = responseEntity.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode zpotsNode = rootNode.path("zpots");

            for (JsonNode zpotNode : zpotsNode) {
                Zpot zpot = new Zpot(
                        zpotNode.path("type").asText(),
                        zpotNode.path("x").asInt(),
                        zpotNode.path("y").asInt()
                );
                zpotList.add(zpot);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return zpotList;
    }
}

