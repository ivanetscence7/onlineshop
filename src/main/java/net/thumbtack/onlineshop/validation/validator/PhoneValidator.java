package net.thumbtack.onlineshop.validation.validator;

import net.thumbtack.onlineshop.validation.constraint.PhoneConstraint;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@Component
public class PhoneValidator implements ConstraintValidator<PhoneConstraint, String> {

    public void initialize(PhoneConstraint constraint) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        String val = "";
        if (value != null) {
            StringBuilder sb = new StringBuilder();
            Arrays.stream(value.split("-")).forEach(sb::append);
            val = sb.toString();
        }
        return (value != null && value.length() >10 && val.length() <= 16 &&
                value.matches("^(?!!@#\\$%\\^&.*\\(\\)$)(?!.*--.*$)(([0-9]{0,1})?([-0-9]{0,1})?([0-9]{0,3})?([-0-9]{0,1})?([0-9]{0,2})?(7[-0-9]{1})?([0-9]{2})?([-0-9]{1})?([0-9]{0,2})?(\\+7[-0-9]{1})?([0-9]{3})?([-0-9]{1})?([0-9]{0,2})?(7[-0-9]{1})?([0-9]{2})?([-0-9]{1})?([0-9]{0,2})?)$"));

    }
}