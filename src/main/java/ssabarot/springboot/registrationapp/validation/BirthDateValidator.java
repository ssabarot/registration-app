package ssabarot.springboot.registrationapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Calendar;
import java.util.Date;

public class BirthDateValidator implements ConstraintValidator<BirthDateConstraint, Date> {
    @Override
    public boolean isValid(final Date valueToValidate, final ConstraintValidatorContext context) {
        if (valueToValidate == null) {
            context.buildConstraintViolationWithTemplate(
                    "The date of birth is required.").addConstraintViolation();
            return false;
        }

        Calendar dateInCalendar = Calendar.getInstance();
        dateInCalendar.setTime(valueToValidate);

        boolean isOfLegalAge = Calendar.getInstance().get(Calendar.YEAR) - dateInCalendar.get(Calendar.YEAR) >= 18;

        if (!isOfLegalAge) {
            context.buildConstraintViolationWithTemplate(
                    "The birth date must be greater or equal than 18.").addConstraintViolation();
            return false;
        }

        return true;
    }
}
