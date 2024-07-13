package com.example.zomnieapp.commands;

import com.example.zomnieapp.app.HeaderConfig;
import com.example.zomnieapp.ui_app.data.model.Coordinate;
import com.example.zomnieapp.units.Base;
import com.example.zomnieapp.units.EnemyBlock;
import com.example.zomnieapp.units.Player;
import com.example.zomnieapp.units.Zombie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.LinkedList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@Component
public class UnitsService {

    private static final String URL = "https://games-test.datsteam.dev/play/zombidef/units";
    private final RestTemplate restTemplate;
    private String responseBody;

    public UnitsService() {
        this.restTemplate = new RestTemplate();
    }

    public void getResponseAndInit() {
        try {
            HttpHeaders headers = HeaderConfig.getAuthHeader();
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, String.class);
            this.responseBody = responseEntity.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Fail to create request to /play/zombidef/units" + e.getMessage());
        }
    }

    public List<Zombie> getZombies() {
        List<Zombie> zombieList = new LinkedList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode zombiesNode = rootNode.path("zombies");

            for (JsonNode zombieNode : zombiesNode) {
                Zombie zombie = new Zombie(
                        zombieNode.path("direction").asText(),
                        zombieNode.path("health").asInt(),
                        zombieNode.path("x").asInt(),
                        zombieNode.path("y").asInt(),
                        zombieNode.path("type").asText(),
                        zombieNode.path("id").asText(),
                        zombieNode.path("attack").asInt(),
                        zombieNode.path("waitTurns").asInt(),
                        zombieNode.path("speed").asInt()
                );
                zombieList.add(zombie);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return zombieList;
    }

    public List<Base> getBases() {
        List<Base> baseList = new LinkedList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode baseNode = rootNode.path("base");

            for (JsonNode baseJson : baseNode) {

                JsonNode lastAttack = baseJson.path("lastAttack");

                Base base = new Base(
                        baseJson.path("id").asText(),
                        baseJson.path("isHead").asBoolean(),
                        baseJson.path("range").asInt(),
                        baseJson.path("x").asInt(),
                        baseJson.path("y").asInt(),
                        baseJson.path("attack").asInt(),
                        baseJson.path("health").asInt(),
                        baseJson.path("name").asText(),
                        new Coordinate(lastAttack.path("x").asInt(), lastAttack.path("y").asInt())
                );
                baseList.add(base);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return baseList;
    }

    public List<EnemyBlock> getEnemyBlocks() {
        List<EnemyBlock> enemyBlockList = new LinkedList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode enemyBlocksNode = rootNode.path("enemyBlocks");

            for (JsonNode enemyBlocksJson : enemyBlocksNode) {
                EnemyBlock enemyBlock = new EnemyBlock(
                        enemyBlocksJson.path("health").asInt(),
                        enemyBlocksJson.path("x").asInt(),
                        enemyBlocksJson.path("y").asInt(),
                        enemyBlocksJson.path("attack").asInt(),
                        enemyBlocksJson.path("name").asText(),
                        enemyBlocksJson.path("isHead").asBoolean()
                );
                enemyBlockList.add(enemyBlock);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return enemyBlockList;
    }

    public Player getPlayer() {
        Player player = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode playerNode = rootNode.path("player");
            player = new Player(playerNode.path("enemyBlockKills").asInt(),
                                playerNode.path("gold").asInt(),
                                playerNode.path("points").asInt(),
                                playerNode.path("zombieKills").asInt());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return player;
    }

    public int getTurnEndsInMs() {
        int turnEndsInMs = -1;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            turnEndsInMs = rootNode.path("turnEndsInMs").asInt();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return turnEndsInMs;
    }
}
