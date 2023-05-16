package org.example.tgservice.kafka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class AppPropConfig {

    public ResourceBundleMessageSource botInfo() {
        var source = new ResourceBundleMessageSource();
        source.setBasenames("messages");
        return source;
    }
}
