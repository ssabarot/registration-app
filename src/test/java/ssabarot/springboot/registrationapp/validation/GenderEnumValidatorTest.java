package ssabarot.springboot.registrationapp.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ssabarot.springboot.registrationapp.model.Gender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ssabarot.springboot.registrationapp.model.Gender.FEMALE;

@SpringBootTest
class GenderEnumValidatorTest {
    private GenderEnumValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        validator = new GenderEnumValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    @DisplayName("Test GenderEnum isValid Success")
    public void testIsValid_ok() {
        // given
        GenderEnumConstraint constraint = mock(GenderEnumConstraint.class);
        when(constraint.anyOf()).thenReturn(new Gender[]{Gender.MALE, Gender.FEMALE, Gender.OTHER});
        validator.initialize(constraint);

        // when
        boolean result = validator.isValid(FEMALE, context);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("Test GenderEnumValidator throws IllegalArgumentException")
    public void testIsValid_thenThrowsException() {
        // given
        GenderEnumConstraint constraint = mock(GenderEnumConstraint.class);
        when(constraint.anyOf()).thenReturn(new Gender[]{Gender.MALE, Gender.FEMALE, Gender.OTHER});
        validator.initialize(constraint);

        // when
        Throwable exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    Gender gender = Gender.valueOf("CAT");
                    validator.isValid(gender, context);
                }
        );

        // then
        assertEquals(IllegalArgumentException.class, exception.getClass());
    }
}