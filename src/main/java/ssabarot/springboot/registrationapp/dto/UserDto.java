package ssabarot.springboot.registrationapp.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ssabarot.springboot.registrationapp.model.Gender;
import ssabarot.springboot.registrationapp.validation.BirthDateConstraint;
import ssabarot.springboot.registrationapp.validation.GenderEnumConstraint;

import java.time.LocalDate;

/**
 * Represents the Data Transfer Object (DTO) to create a user.
 */
@Data
@Builder
@GroupSequence({UserDto.class, UserDto.SecondStepValidation.class})
public class UserDto {
    private Long id;

    @NotEmpty(message = "The name is required.")
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "The date of birth is required.")
    @BirthDateConstraint(groups = SecondStepValidation.class)
    private LocalDate birthdate;

    @Pattern(groups = SecondStepValidation.class, regexp = "France", flags = Pattern.Flag.CASE_INSENSITIVE, message = "The user must be residing in France.")
    @NotEmpty(message = "The country is required.")
    private String country;

    @Pattern(regexp = "^(\\+33|0)\\d{9}$", message = "The phone number should start with '+33' or '0' and followed by 9 digits.")
    private String phoneNumber;

    @GenderEnumConstraint(anyOf = {Gender.MALE, Gender.FEMALE, Gender.OTHER})
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public interface SecondStepValidation {
    }

}
