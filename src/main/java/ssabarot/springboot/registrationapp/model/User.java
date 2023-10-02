package ssabarot.springboot.registrationapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import ssabarot.springboot.registrationapp.validation.BirthDateConstraint;
import ssabarot.springboot.registrationapp.validation.GenderEnumConstraint;

import java.util.Date;

/**
 * Represents an Entity "User" to persist the information.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The name is required.")
    @Column(name="name", nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @BirthDateConstraint()
    @Column(name="birthdate", nullable = false)
    private Date birthdate;

    @Pattern(regexp = "France", flags = Pattern.Flag.CASE_INSENSITIVE, message = "The user must be residing in France.")
    @NotEmpty(message = "The country is required.")
    @Column(name="country", nullable = false)
    private String country;

    @Pattern(regexp = "^(\\+33|0)\\d{9}$", message = "The phone number should start with '+33' or '0' and followed by 9 digits.")
    @Column(name="phoneNumber")
    private String phoneNumber;

    @GenderEnumConstraint(anyOf = {Gender.MALE, Gender.FEMALE, Gender.OTHER})
    @Column(name="gender")
    private Gender gender;
}
