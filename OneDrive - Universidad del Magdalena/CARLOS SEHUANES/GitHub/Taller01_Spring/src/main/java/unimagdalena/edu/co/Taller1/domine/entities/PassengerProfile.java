package unimagdalena.edu.co.Taller1.domine.entities;

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
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String phone;
    private String CountryCode;

    @OneToOne(optional = false)
    @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    private Passenger passenger;

}
