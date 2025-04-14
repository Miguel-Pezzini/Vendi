package com.vendi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PositiveFloatValidator implements ConstraintValidator<PositiveFloat, Float> {
    @Override
    public boolean isValid(Float value, ConstraintValidatorContext context) {
        return value != null && value > 0;
    }
}