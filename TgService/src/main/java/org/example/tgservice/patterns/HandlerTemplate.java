package org.example.tgservice.patterns;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HandlerTemplate {

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> createResponse(String action, String... variables){

        StringBuilder urlPattern = new StringBuilder("http://localhost:8080/user/"+action);
        for (String obj: variables) {
            urlPattern.append("/");
            urlPattern.append(obj);
        }

        return restTemplate.getForEntity(urlPattern.toString(), String.class);
    }

}
