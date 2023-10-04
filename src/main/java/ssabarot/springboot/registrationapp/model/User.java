package ssabarot.springboot.registrationapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @Column(name = "name", nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "gender")
    private Gender gender;
}
