package com.dwigs.biblioteca.validator.coordinates;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CoordinatesValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CoordinatesConstraint {
    String message() default "Las coordenadas deben ser válidas";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
