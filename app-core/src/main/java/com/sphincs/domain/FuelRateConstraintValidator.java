package com.sphincs.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FuelRateConstraintValidator implements ConstraintValidator<FuelRate, Double> {

    @Override
    public void initialize(FuelRate fuelRate) {
    }

    @Override
    public boolean isValid(Double fuelRate, ConstraintValidatorContext constraintValidatorContext) {
        if ((fuelRate == null) || (fuelRate < 1) || (fuelRate > 40)) return false;
        return true;
    }

}
