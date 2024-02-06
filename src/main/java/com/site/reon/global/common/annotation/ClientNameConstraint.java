package com.site.reon.global.common.annotation;


import com.site.reon.global.common.validator.ClientNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Constraint(validatedBy = ClientNameValidator.class)
@Target({ FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ClientNameConstraint {
    String message() default "Invalid client name.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
