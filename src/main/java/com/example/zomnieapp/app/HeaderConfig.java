package com.example.zomnieapp.app;

import org.springframework.http.HttpHeaders;

public class HeaderConfig {
    public static HttpHeaders getAuthHeader(){
        var header = new HttpHeaders();
        header.set("X-Auth-Token", "668fefd4907eb668fefd4907ef");
        return header;
    }
}
