package io.proj3ct.springdemobot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class AppPropConfig {

    @Bean
    public ResourceBundleMessageSource adminInformation() {

        var source = new ResourceBundleMessageSource();
        source.setBasenames("bot");
        return source;
    }

    @Bean
    public ResourceBundleMessageSource botInfo() {
        var source = new ResourceBundleMessageSource();
        source.setBasenames("messages");
        return source;
    }
}
