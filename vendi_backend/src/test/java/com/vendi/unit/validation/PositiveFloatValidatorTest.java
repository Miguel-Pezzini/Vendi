package com.vendi.unit.validation;

import com.vendi.validation.PositiveFloatValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PositiveFloatValidatorTest {

    private final PositiveFloatValidator validator = new PositiveFloatValidator();

    @Test
    void acceptsOnlyPositiveNonNullValues() {
        assertTrue(validator.isValid(1.5f, null));
        assertFalse(validator.isValid(0f, null));
        assertFalse(validator.isValid(-2f, null));
        assertFalse(validator.isValid(null, null));
    }
}
