package com.example.zomnieapp.app;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static com.example.zomnieapp.ZomnieAppApplication.HOST_URL;

@Component
public class Registration {

    private final RestTemplate restTemplate;
    private static final String URL = HOST_URL + "/play/zombidef/participate";
    private boolean completedRegistration = false;

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
            return false;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return false;
        }
    }
}
