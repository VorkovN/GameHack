package com.example.zomnieapp.app;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class Registration {

    private final RestTemplate restTemplate;
    private static final String URL = "https://games-test.datsteam.dev/play/zombidef/participate";

    public Registration() {
        this.restTemplate = new RestTemplate();
    }

    public boolean registration() {
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(HeaderConfig.getAuthHeader());
            ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, requestEntity, String.class);

            System.out.println(requestEntity.hashCode());
            System.out.println(responseEntity.getBody());

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return true;
            } else {
                System.out.println("Unexpected response status: " + responseEntity.getStatusCode());
                return false;
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.out.println("HTTP Status Code: " + e.getStatusCode());
            System.out.println("HTTP Response Body: " + e.getResponseBodyAsString());
            return false;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return false;
        }
    }
}
