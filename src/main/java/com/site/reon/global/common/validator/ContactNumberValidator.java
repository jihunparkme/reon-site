package com.site.reon.global.common.validator;

import com.site.reon.global.common.annotation.ClientIdConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContactNumberValidator implements ConstraintValidator<ClientIdConstraint, String> {

    @Override
    public void initialize(ClientIdConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext context) {
        return contactField != null;
    }
}
