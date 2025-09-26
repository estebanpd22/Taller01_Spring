package unimagdalena.edu.co.Taller1.domine.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "Airports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;
    private String name;
    private String city;

    @OneToMany(mappedBy = "origin", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Flight> departures;

    @OneToMany(mappedBy = "destination", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Flight> arrivals;
}

