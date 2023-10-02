package ssabarot.springboot.registrationapp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ssabarot.springboot.registrationapp.model.Gender;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = GenderEnumValidator.class)
public @interface GenderEnumConstraint {

    Gender[] anyOf();
    String message() default "Gender must be any of {anyOf}.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}