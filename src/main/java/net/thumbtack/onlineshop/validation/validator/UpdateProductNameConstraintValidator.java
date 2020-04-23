package net.thumbtack.onlineshop.validation.validator;

import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.validation.constraint.UpdateProductNameConstraint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpdateProductNameConstraintValidator implements ConstraintValidator<UpdateProductNameConstraint, String> {

    @Autowired
    private AppProperties properties;

    @Override
    public void initialize(UpdateProductNameConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        } else {
            return (value.matches("^([А-Я]{1}[а-я]{" + properties.getMinNameLength() + "," + properties.getMaxNameLength() + "}?$)|(^([А-Я]{1}[а-я]{" + properties.getMinNameLength() + "," + properties.getMaxNameLength() + "}[-]{1}[А-Я]{1}[а-я]{" + properties.getMinNameLength() + "," + properties.getMaxNameLength() + "}))$"));
        }
    }
}
