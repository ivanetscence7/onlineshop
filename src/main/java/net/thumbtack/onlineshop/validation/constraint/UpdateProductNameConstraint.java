package net.thumbtack.onlineshop.validation.constraint;


import net.thumbtack.onlineshop.validation.validator.UpdateProductNameConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = UpdateProductNameConstraintValidator.class)
@Target({ElementType.FIELD})
@Retention(RUNTIME)
public @interface UpdateProductNameConstraint {
    String message() default "Invalid name for update";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

