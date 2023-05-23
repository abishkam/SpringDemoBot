package org.example.tgservice.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.example.tgservice.config.BotProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * BotProperties has notBlank fields.
 */
public class BotPropertiesValidatorTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final List<String> required = Arrays.asList("token", "name", "owner");

    @Test
    public void testEn() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        var properties = new BotProperties();
        properties.setToken("token");
        properties.setOwner("owner");
        properties.setName("name");
        var errors = validator.validate(properties);
        Assertions.assertEquals(0, errors.size(), "Without errors");

        properties = new BotProperties();
        errors = validator.validate(properties);
        ConstraintViolation[] constraintViolations = errors.toArray(ConstraintViolation[]::new);
        for (ConstraintViolation violation : constraintViolations) {
            Assertions.assertEquals("must not be blank", violation.getMessage());
            Assertions.assertTrue(required.contains(violation.getPropertyPath().toString()));
        }
    }
}
