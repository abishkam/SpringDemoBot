package org.example.tgservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class TgServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TgServiceApplication.class, args);
    }
}
