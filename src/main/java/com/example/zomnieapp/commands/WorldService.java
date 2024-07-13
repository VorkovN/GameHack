package com.example.zomnieapp.commands;

import com.example.zomnieapp.app.HeaderConfig;
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
public class WorldService {

    private static final String URL = "https://games.datsteam.dev/play/zombidef/world";
    private final RestTemplate restTemplate;
    private String responseBody;

    public WorldService() {
        this.restTemplate = new RestTemplate();
    }

    public void getResponseAndInit() {
        try {
            HttpHeaders headers = HeaderConfig.getAuthHeader();
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, String.class); //
            this.responseBody = responseEntity.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Fail to create request to /play/zombidef/world" + e.getMessage());
        }
    }

    public List<Zpot> getZpots() {
        List<Zpot> zpotList = new ArrayList<>();
        try {

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

