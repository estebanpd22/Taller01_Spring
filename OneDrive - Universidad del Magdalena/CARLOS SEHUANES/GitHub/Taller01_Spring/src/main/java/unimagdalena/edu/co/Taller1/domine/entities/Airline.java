package unimagdalena.edu.co.Taller1.domine.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "Airlines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;
    private String name;

    @OneToMany(mappedBy = "airline", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Flight> flights;
}

