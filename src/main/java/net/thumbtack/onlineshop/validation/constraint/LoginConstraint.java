package net.thumbtack.onlineshop.validation.constraint;

import net.thumbtack.onlineshop.validation.validator.LoginConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = LoginConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RUNTIME)
public @interface LoginConstraint {
    String message() default "Invalid login";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
