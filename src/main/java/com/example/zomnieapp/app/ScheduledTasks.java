package com.example.zomnieapp.app;

import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class ScheduledTasks {

    private final RestTemplate restTemplate;

    public ScheduledTasks() {
        this.restTemplate = new RestTemplate();
    }

    @Scheduled(cron = "*/1 * * * * *")
    public void sendPutRequest() {
        String url = "https://games-test.datsteam.dev/play/zombidef/participate";

        HttpEntity<String> requestEntity = new HttpEntity<>(HeaderConfig.getAuthHeader());
//        ResponseEntity<?> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity);

//        System.out.println(responseEntity.getBody());
    }


}
