package org.example.tgservice.patterns;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HandlerTemplate {

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> userResponse(String action, String... variables){

        String urlPattern = responsePattern("user", action, variables);

        return restTemplate.getForEntity(urlPattern, String.class);
    }

    public ResponseEntity<String> memorizationResponse(String action, String... variables){

        String urlPattern = responsePattern("memorization", action, variables);

        return restTemplate.getForEntity(urlPattern, String.class);
    }

    public ResponseEntity<String> timeResponse(String action, String... variables){

        String urlPattern = responsePattern("time", action, variables);

        return restTemplate.getForEntity(urlPattern, String.class);
    }

    public String responsePattern(String controller, String action ,String... variables){

        StringBuilder urlPattern = new StringBuilder("http://localhost:8080/")
                .append(controller)
                .append("/")
                .append(action);

        for (String obj: variables) {
            urlPattern.append("/")
                    .append(obj);
        }

        return urlPattern.toString();
    }


}
