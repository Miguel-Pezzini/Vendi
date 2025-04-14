package com.vendi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PositiveFloatValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveFloat {
    String message() default "The value must be positive";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}