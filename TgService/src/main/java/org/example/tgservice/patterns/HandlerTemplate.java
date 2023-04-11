package org.example.tgservice.patterns;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HandlerTemplate {

    private final RestTemplate restTemplate = new RestTemplate();

    public <T> ResponseEntity<T> createResponse(Class clazz, String action, String... variables){

        StringBuilder urlPattern = new StringBuilder("http://localhost:8080/user/"+action);
        for (String obj: variables) {
            urlPattern.append("/"+obj);
        }

        return restTemplate.getForEntity(urlPattern.toString(), clazz);
    }

}
