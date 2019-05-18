package net.thumbtack.onlineshop.validation.constraint;

import net.thumbtack.onlineshop.validation.validator.LoginValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = LoginValidator.class)
@Target({ElementType.FIELD})
public @interface LoginConstraint {
    String message() default "Invalid login";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
