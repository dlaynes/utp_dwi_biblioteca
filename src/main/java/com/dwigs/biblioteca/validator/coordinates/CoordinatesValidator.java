package com.dwigs.biblioteca.validator.coordinates;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class CoordinatesValidator implements ConstraintValidator<CoordinatesConstraint, String> {
    public static final String DD_COORDINATE_REGEX = "^(-?\\d+\\.\\d+)(\\s*,\\s*)?(-?\\d+\\.\\d+)$";

    @Override
    public void initialize(CoordinatesConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return isValidDDFormat(s);
    }

    boolean isValidDDFormat(String coordinateString) {
        return Pattern.compile(DD_COORDINATE_REGEX).matcher(coordinateString).matches();
    }
}

