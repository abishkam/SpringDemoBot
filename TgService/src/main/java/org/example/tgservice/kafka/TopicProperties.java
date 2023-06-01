package org.example.tgservice.kafka;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
public class TopicProperties {

    @NotBlank
    private String bdTopic;
    @NotBlank
    private String serviceTopic;

}
