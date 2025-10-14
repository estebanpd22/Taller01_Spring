package unimagdalena.edu.co.Taller1.entities;


import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Airports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id")
    private Long id;
    @Column(unique = true)
    private String code;
    @Column(nullable = false)
    private String name;
    private String city;
}
