package ssabarot.springboot.registrationapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class BirthDateValidator implements ConstraintValidator<BirthDateConstraint, LocalDate> {
    @Override
    public boolean isValid(final LocalDate valueToValidate, final ConstraintValidatorContext context) {
        if (valueToValidate == null) {
            return false;
        }

        return calculateAge(valueToValidate) >= 18;
    }

    public int calculateAge(
            LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
