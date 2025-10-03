package unimagdalena.edu.co.Taller1.domine.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import unimagdalena.edu.co.Taller1.domine.entities.*;
import unimagdalena.edu.co.Taller1.domine.repositories.*;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class BookingRepositoryTest extends AbstractRepositoryTI {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private BookingItemRepository bookingItemRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirportRepository airportRepository;

    // ---------- HELPERS ----------

    private Passenger createPassenger(String email) {
        return passengerRepository.save(
                Passenger.builder()
                        .email(email)
                        .fullName("Tester Passenger")
                        .profile(PassengerProfile.builder()
                                .phone("111")
                                .countryCode("57")
                                .build())
                        .build()
        );
    }

    private Booking createBooking(Passenger passenger, OffsetDateTime createdAt) {
        return bookingRepository.save(
                Booking.builder()
                        .createdAt(createdAt)
                        .passenger(passenger)
                        .build()
        );
    }

    private Flight createFlight(String number, String origin, String destination) {
        return flightRepository.save(
                Flight.builder()
                        .number(number)
                        .origin(airportRepository.save(Airport.builder().name(origin).build()))
                        .destination(airportRepository.save(Airport.builder().name(destination).build()))
                        .build()
        );
    }

    // ---------- TESTS ----------

    @Test
    @DisplayName("BookingRepository: pagina reservas de un pasajero ordenadas desc por createdAt")
    void findByPassenger_EmailIgnoreCaseOrderByCreatedAtDesc() {
        // GIVEN
        var passenger = createPassenger("page@test.com");

        var booking1 = createBooking(passenger, OffsetDateTime.now().minusDays(2));
        var booking2 = createBooking(passenger, OffsetDateTime.now().minusDays(1));
        var booking3 = createBooking(passenger, OffsetDateTime.now());

        Pageable pageable = PageRequest.of(0, 2);

        // WHEN
        var page = bookingRepository.findByPassenger_EmailIgnoreCaseOrderByCreatedAtDesc(
                "PAGE@test.com", // email en mayúsculas para probar IgnoreCase
                pageable
        );

        // THEN
        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getContent().get(0).getId()).isEqualTo(booking3.getId());
        assertThat(page.getContent().get(1).getId()).isEqualTo(booking2.getId());
        assertThat(page.getTotalElements()).isEqualTo(3);
    }

    @Test
    @DisplayName("BookingRepository: carga gráfica con items, flights y passenger")
    void fetchGraphById() {
        // GIVEN
        var passenger = createPassenger("graph@test.com");
        var booking = createBooking(passenger, OffsetDateTime.now());

        var flight = createFlight("F123", "Dorado", "Nevado");

        bookingItemRepository.save(
                BookingItem.builder()
                        .segmentOrder(1)
                        .cabin(Cabin.ECONOMY)
                        .price(new java.math.BigDecimal("100"))
                        .booking(booking)
                        .flight(flight)
                        .build()
        );

        // WHEN
        var found = bookingRepository.fetchGraphById(booking.getId());

        // THEN
        assertThat(found).isNotNull();
        assertThat(found.get().getPassenger()).isNotNull();
        assertThat(found.get().getItems()).isNotEmpty();
        assertThat(found.get().getItems().getFirst().getFlight()).isNotNull();
    }
}
