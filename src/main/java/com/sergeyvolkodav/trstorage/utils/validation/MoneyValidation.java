package com.sergeyvolkodav.trstorage.utils.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoneyValidation implements ConstraintValidator<CheckMoney, Double> {

    private CheckMoney amount;
    private final String regExp = "^[0-9]*.[0-9]{0,2}$";

    @Override
    public void initialize(CheckMoney constraintAnnotation) {
        this.amount = constraintAnnotation;
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null)
            return true;
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(String.valueOf(value));
        return matcher.matches();
    }
}
