package unimagdalena.edu.co.Taller1.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PassengerProfiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, length = 5)
    private String countryCode;

    @OneToOne(optional = false)
    @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    private Passenger passenger;
}

