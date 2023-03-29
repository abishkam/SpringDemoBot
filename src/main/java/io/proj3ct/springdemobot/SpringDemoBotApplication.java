package io.proj3ct.springdemobot;

import io.proj3ct.springdemobot.config.BotConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableConfigurationProperties(BotConfig.class)
public class SpringDemoBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDemoBotApplication.class, args);
    }

}
