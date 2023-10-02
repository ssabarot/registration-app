package ssabarot.springboot.registrationapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ssabarot.springboot.registrationapp.model.Gender;

import java.util.Arrays;

public class GenderEnumValidator implements ConstraintValidator<GenderEnumConstraint, Gender> {
    private Gender[] enumTypes;

    @Override
    public void initialize(GenderEnumConstraint constraint) {

        this.enumTypes = constraint.anyOf();
    }

    @Override
    public boolean isValid(Gender gender, ConstraintValidatorContext context) {

        return gender == null || Arrays.asList(enumTypes).contains(gender);
    }
}
