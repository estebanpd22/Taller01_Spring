package unimagdalena.edu.co.Taller1.domine.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SeatInventories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Cabin cabin;
    private Integer totalSeats;
    private Integer availableSeats;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
}

