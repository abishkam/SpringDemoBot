package io.proj3ct.springdemobot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

public class MainClass {
    @Autowired
    private MessageSource messageSource;

    public MainClass() {
    }

    public static void main(String[] args) {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("messages");
        System.out.println(source.getMessage("welcome.message", null, Locale.ENGLISH));
    }
}
