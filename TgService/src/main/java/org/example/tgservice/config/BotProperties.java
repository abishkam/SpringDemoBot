package org.example.tgservice.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "bot")
@Getter
@Setter
@Validated
public class BotProperties {

    @NotBlank
    private String token;
    @NotBlank
    private String name;
    @NotBlank
    private String owner;

}
