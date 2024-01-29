package com.site.reon.global.common.validator;

import com.site.reon.global.common.annotation.ClientNameConstraint;
import com.site.reon.global.common.property.ReonAppProperty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientNameValidator implements ConstraintValidator<ClientNameConstraint, String> {

    private final ReonAppProperty reonAppProperty;

    @Override
    public void initialize(ClientNameConstraint clientName) {
    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext context) {
        return contactField != null && contactField.equals(reonAppProperty.getClientName());
    }
}
