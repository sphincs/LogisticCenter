package com.sphincs.domain;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FuelRateConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FuelRate {

    String message() default "FuelRate must be between 1 and 40 litres per 100 km. ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
