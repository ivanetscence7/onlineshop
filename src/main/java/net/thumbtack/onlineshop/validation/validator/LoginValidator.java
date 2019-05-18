package net.thumbtack.onlineshop.validation.validator;

import net.thumbtack.onlineshop.config.Config;
import net.thumbtack.onlineshop.validation.constraint.LoginConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginValidator implements ConstraintValidator<LoginConstraint, String> {

    @Override
    public void initialize(LoginConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("[А-Яёа-яA-Za-z0-9]")
                && (value.length() > Config.min_name_length) && (value.length() < Config.max_name_length);
    }
}
