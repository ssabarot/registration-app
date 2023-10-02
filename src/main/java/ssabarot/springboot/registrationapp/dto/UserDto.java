package ssabarot.springboot.registrationapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import ssabarot.springboot.registrationapp.model.Gender;
import ssabarot.springboot.registrationapp.validation.BirthDateConstraint;
import ssabarot.springboot.registrationapp.validation.GenderEnumConstraint;

import java.util.Date;

/**
 * Represents the Data Transfer Object (DTO) to create a user.
 */
@Data
@Builder
public class UserDto {
    private Long id;

    @NotEmpty(message = "The name is required.")
    private String name;

    @Past(message = "The date of birth must be in the past.")
    @BirthDateConstraint()
    private Date birthdate;

    @Pattern(regexp = "France", flags = Pattern.Flag.CASE_INSENSITIVE, message = "The user must be residing in France.")
    @NotEmpty(message = "The country is required.")
    private String country;

    @Pattern(regexp = "^(\\+33|0)\\d{9}$", message = "The phone number should start with '+33' or '0' and followed by 9 digits.")
    private String phoneNumber;

    @GenderEnumConstraint(anyOf = {Gender.MALE, Gender.FEMALE, Gender.OTHER})
    private Gender gender;
}
