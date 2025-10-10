package unimagdalena.edu.co.Taller1.domine.entities;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BookingItems")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_item_id")
    private Long id;

    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer segmentOrder;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cabin cabin;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    private Flight flight;
}