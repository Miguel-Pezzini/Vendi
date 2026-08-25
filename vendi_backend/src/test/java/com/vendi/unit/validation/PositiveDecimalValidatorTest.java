package com.vendi.unit.validation;

import com.vendi.validation.PositiveDecimalValidator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PositiveDecimalValidatorTest {

    private final PositiveDecimalValidator validator = new PositiveDecimalValidator();

    @Test
    void acceptsOnlyPositiveNonNullValues() {
        assertTrue(validator.isValid(new BigDecimal("1.50"), null));
        assertFalse(validator.isValid(BigDecimal.ZERO, null));
        assertFalse(validator.isValid(new BigDecimal("-2"), null));
        assertFalse(validator.isValid(null, null));
    }
}
