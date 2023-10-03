package ssabarot.springboot.registrationapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class BirthDateValidator implements ConstraintValidator<BirthDateConstraint, LocalDate> {
    @Override
    public boolean isValid(final LocalDate valueToValidate, final ConstraintValidatorContext context) {
        if (valueToValidate == null) {
            context.buildConstraintViolationWithTemplate(
                    "The date of birth is required.").addConstraintViolation();
            return false;
        }

        boolean isOfLegalAge = calculateAge(valueToValidate) >= 18;

        if (!isOfLegalAge) {
            context.buildConstraintViolationWithTemplate(
                    "The birth date must be greater or equal than 18.").addConstraintViolation();
            return false;
        }

        return true;
    }

    public int calculateAge(
            LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
