package net.thumbtack.onlineshop.validation.validator;

import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.validation.constraint.LoginConstraint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginConstraintValidator implements ConstraintValidator<LoginConstraint, String> {

    @Autowired
    private AppProperties properties;

    @Override
    public void initialize(LoginConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("^([А-Яёа-яA-Za-z0-9]{1," + properties.getMaxNameLength() + "})$") &&
                (value.length() >= properties.getMinNameLength()) && (value.length() <= properties.getMaxNameLength());
    }
}
