package ssabarot.springboot.registrationapp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = BirthDateValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BirthDateConstraint {
    String message() default "";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}
