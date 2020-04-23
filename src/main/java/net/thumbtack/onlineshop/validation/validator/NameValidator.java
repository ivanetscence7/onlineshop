package net.thumbtack.onlineshop.validation.validator;

import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.validation.constraint.NameConstraint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<NameConstraint, String> {

    @Autowired
    private AppProperties properties;

    @Override
    public void initialize(NameConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value != null){
            return (value.matches("^([А-Я]{1}[а-я]{" + properties.getMinNameLength() + "," + AppProperties.getMaxNameLength() + "}?$)|(^([А-Я]{1}[а-я]{" + properties.getMinNameLength() + "," + AppProperties.getMaxNameLength() + "}[-]{1}[А-Я]{1}[а-я]{" + properties.getMinNameLength() + "," + AppProperties.getMaxNameLength() + "}))$"));
        }
        return true;
    }
}
