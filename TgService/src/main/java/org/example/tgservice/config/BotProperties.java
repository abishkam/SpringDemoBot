package org.example.tgservice.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bot")
@Getter
@Setter
public class BotProperties {

    @NotBlank
    private String token;
    @NotBlank
    private String name;
    @NotBlank
    private String owner;

}
