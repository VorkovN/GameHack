package com.example.zomnieapp.app;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static com.example.zomnieapp.ui_app.MainFrame.dataRepository;

@Component
public class Registration {

    private final RestTemplate restTemplate;
    private static final String URL = "https://games-test.datsteam.dev/play/zombidef/participate";
    private boolean completedRegistration = true;

    public Registration() {
        this.restTemplate = new RestTemplate();
    }

    public boolean registration() {
        if (completedRegistration) return true;
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(HeaderConfig.getAuthHeader());
            ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, requestEntity, String.class);

            System.out.println(requestEntity.hashCode());
            String data = responseEntity.getBody();
            System.out.println(data);
            dataRepository.newTexts(Collections.singletonList(data));

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                completedRegistration = true;
                return true;
            } else {
                System.out.println("Unexpected response status: " + responseEntity.getStatusCode());
                return false;
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            String data = "HTTP Status Code: " + e.getStatusCode() +"\n" +"HTTP Response Body: " + e.getResponseBodyAsString();
            System.out.println(data);
            dataRepository.newTexts(Collections.singletonList(data));
            return false;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return false;
        }
    }
}
