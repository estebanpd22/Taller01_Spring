package unimagdalena.edu.co.Taller1.domine.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String number;
    private OffsetDateTime departureTime;
    private OffsetDateTime arrivalTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airline_id", referencedColumnName = "id")
    private Airline airline;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "origin_id", referencedColumnName = "id")
    private Airport origin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    private Airport destination;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Flight_Tags",
            joinColumns = @JoinColumn(name = "flight_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )

    @Builder.Default
    private Set<Tag> tags;

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getFlights().add(this);
    }
}
