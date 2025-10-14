package unimagdalena.edu.co.Taller1.entities;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Airlines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airline_id")
    private Long id;
    @Column(nullable = false,unique = true)
    private String code;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "airline", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Flight> flights =  new ArrayList<>();

    public void addFlight(Flight f) {
        flights.add(f);
        f.setAirline(this);
    }
}
