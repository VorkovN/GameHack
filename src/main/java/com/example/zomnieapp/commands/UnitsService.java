package com.example.zomnieapp.commands;

import com.example.zomnieapp.app.HeaderConfig;
import com.example.zomnieapp.units.Base;
import com.example.zomnieapp.units.EnemyBlock;
import com.example.zomnieapp.units.Zombie;
import org.springframework.stereotype.Component;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Component
public class UnitsService {

    private static final String URL = "https://games-test.datsteam.dev/play/zombidef/units";
    private final RestTemplate restTemplate;

    public UnitsService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Zombie> getZombies() {
        List<Zombie> zombieList = new ArrayList<>();
        try {
            HttpHeaders headers = HeaderConfig.getAuthHeader();
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, String.class); //
            String responseBody = responseEntity.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode zombiesNode = rootNode.path("zombies");

            for (JsonNode zombieNode : zombiesNode) {
                Zombie zombie = new Zombie(
                        zombieNode.path("direction").asInt(),
                        zombieNode.path("health").asInt(),
                        zombieNode.path("x").asInt(),
                        zombieNode.path("y").asInt()
                );
                zombieList.add(zombie);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return zombieList;
    }

    public List<Base> getBases() {
        List<Base> baseList = new ArrayList<>();
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(HeaderConfig.getAuthHeader());
            ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, String.class);
            String responseBody = responseEntity.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode baseNode = rootNode.path("base");

            for (JsonNode baseJson : baseNode) {

                Base base = new Base(
                        baseJson.path("id").asText(),
                        baseJson.path("isHead").asBoolean(),
                        baseJson.path("x").asInt(),
                        baseJson.path("y").asInt()
                );
                baseList.add(base);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return baseList;
    }

    public List<EnemyBlock> getEnemyBlocks() {
        List<EnemyBlock> enemyBlockList = new ArrayList<>();
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(HeaderConfig.getAuthHeader());
            ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, String.class);
            String responseBody = responseEntity.getBody();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode enemyBlocksNode = rootNode.path("enemyBlocks");

            for (JsonNode enemyBlocksJson : enemyBlocksNode) {
                EnemyBlock enemyBlock = new EnemyBlock(
                        enemyBlocksJson.path("x").asInt(),
                        enemyBlocksJson.path("y").asInt()
                );
                enemyBlockList.add(enemyBlock);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return enemyBlockList;
    }
}
