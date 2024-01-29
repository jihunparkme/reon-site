package com.site.reon.global.common.validator;

import com.site.reon.global.common.annotation.ClientIdConstraint;
import com.site.reon.global.common.property.AppReonProperty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientIdValidator implements ConstraintValidator<ClientIdConstraint, String> {

    private final AppReonProperty appReonProperty;

    @Override
    public void initialize(ClientIdConstraint clientId) {
    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext context) {
        return contactField != null && contactField.equals(appReonProperty.getClientId());
    }
}
