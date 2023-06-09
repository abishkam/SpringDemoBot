package org.example.tgbd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class TgBdApplication {
    public static void main(String[] args) {
        SpringApplication.run(TgBdApplication.class, args);
    }
}
