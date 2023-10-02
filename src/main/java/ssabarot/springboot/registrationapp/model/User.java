package ssabarot.springboot.registrationapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;

/**
 * Represents an Entity "User" to persist the information.
 */

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @Column(name="birthdate", nullable = false)
    private Date birthdate;

    @Column(name="country", nullable = false)
    private String country;

    @Pattern(regexp = "^(\\+33|0)\\d{9}$", message = "The phone number should start with '+33' or '0' and followed by 9 digits.")
    @Column(name="phoneNumber")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    private Gender gender;
}
