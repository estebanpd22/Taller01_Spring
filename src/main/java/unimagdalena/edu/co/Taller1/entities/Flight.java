package unimagdalena.edu.co.Taller1.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Flights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String number;
    @Column( name = "departure_time")
    private OffsetDateTime departureTime;
    @Column(name = "arrival_time")
    private OffsetDateTime arrivalTime;

    @ManyToOne
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

    @ManyToOne
    @JoinColumn(name = "origin_airport_id", nullable = false)
    private Airport origin;

    @ManyToOne
    @JoinColumn(name = "destination_airport_id", nullable = false)
    private Airport destination;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "flight_tags",
            joinColumns = @JoinColumn(name = "flight_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
    private Set<Tag> tags = new HashSet<>();

    // helper
    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getFlights().add(this);
    }

}
