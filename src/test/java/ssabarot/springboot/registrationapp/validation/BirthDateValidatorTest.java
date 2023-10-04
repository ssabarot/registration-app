package ssabarot.springboot.registrationapp.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class BirthDateValidatorTest {
    private BirthDateValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        validator = new BirthDateValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    @DisplayName("Test BirthDate isValid Success")
    public void testIsValid_ok() {
        // given
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);

        when(context.getDefaultConstraintMessageTemplate()).thenReturn("");
        when(context.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(builder);

        LocalDate birthdate = LocalDate.of(2001, 9, 14);

        // when
        boolean result = validator.isValid(birthdate, context);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("Test BirthDate isValid Fail - BirthDate is not in the past")
    public void testIsValid_birthdate_not_in_the_past() {
        // given
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);

        when(context.getDefaultConstraintMessageTemplate()).thenReturn("");
        when(context.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(builder);

        LocalDate birthdate = LocalDate.of(2442, 9, 24);
        // when
        boolean result = validator.isValid(birthdate, context);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("Test BirthDate isValid Fail - user under 18yo")
    public void testIsValid_user_under_18_yo() {
        // given
        ConstraintValidatorContext.ConstraintViolationBuilder builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);

        when(context.getDefaultConstraintMessageTemplate()).thenReturn("");
        when(context.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(builder);

        LocalDate birthdate = LocalDate.of(2018, 9, 14);

        // when
        boolean result = validator.isValid(birthdate, context);

        // then
        assertFalse(result);
    }
}