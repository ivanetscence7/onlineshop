package net.thumbtack.onlineshop.validation.validator;

import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.validation.constraint.PasswordConstraint;
import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Autowired
    private AppProperties properties;

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(properties.getMinPasswordLength(), properties.getMaxNameLength()),
                new UppercaseCharacterRule(1),
                new DigitCharacterRule(1),
                new SpecialCharacterRule(1)
        ));

        RuleResult result = validator.validate(new PasswordData(password));
        return result.isValid();

    }
}
