package unimagdalena.edu.co.Taller1.domine.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @OneToMany(mappedBy = "booking",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @Builder.Default
    private List<BookingItem> items = new ArrayList<>();

    public void addItem(BookingItem item) {
        this.items.add(item);
        item.setBooking(this);
    }

}