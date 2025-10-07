package unimagdalena.edu.co.Taller1.domine.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import unimagdalena.edu.co.Taller1.domine.entities.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BookingItemRepositoryTest extends AbstractRepositoryTI {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private BookingItemRepository bookingItemRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirportRepository airportRepository;

    // ---------- HELPERS ----------

    private Passenger createPassenger(String email, String name) {
        return passengerRepository.save(
                Passenger.builder()
                        .email(email)
                        .fullName(name)
                        .profile(PassengerProfile.builder()
                                .phone("111")
                                .countryCode("57")
                                .build())
                        .build()
        );
    }

    private Booking createBooking(Passenger passenger) {
        return bookingRepository.save(
                Booking.builder()
                        .createdAt(OffsetDateTime.now())
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
    @DisplayName("BookingItem: lista segmentos ordenados por segmentOrder")
    void shouldListItemsOrderedBySegmentOrder() {
        // GIVEN
        var passenger = createPassenger("estebancamilop22@gmail.com", "LoxPd");
        var booking = createBooking(passenger);

        var flight1 = createFlight("F100", "dorado", "nevado");
        var flight2 = createFlight("F200", "Puellito", "airSantaMarta");

        bookingItemRepository.saveAll(List.of(
                BookingItem.builder()
                        .segmentOrder(2).cabin(Cabin.BUSINESS).price(new BigDecimal("100"))
                        .booking(booking).flight(flight2).build(),
                BookingItem.builder()
                        .segmentOrder(1).cabin(Cabin.ECONOMY).price(new BigDecimal("300"))
                        .booking(booking).flight(flight1).build()
        ));

        // WHEN
        List<BookingItem> items = bookingItemRepository.findByBooking_IdOrderBySegmentOrder(booking.getId());

        // THEN
        assertThat(items).hasSize(2);
        assertThat(items.get(0).getSegmentOrder()).isEqualTo(1);
        assertThat(items.get(1).getSegmentOrder()).isEqualTo(2);
    }

    @Test
    @DisplayName("BookingItem: suma precios de los items de una reserva")
    void shouldCalculateTotalPriceByBookingId() {
        // GIVEN
        var passenger = createPassenger("sum@test.com", "Sum Tester");
        var booking = createBooking(passenger);
        var flight = createFlight("F300", "Simon_bolivar", "nevado");

        bookingItemRepository.saveAll(List.of(
                BookingItem.builder().segmentOrder(1).cabin(Cabin.ECONOMY).price(new BigDecimal("250"))
                        .booking(booking).flight(flight).build(),
                BookingItem.builder().segmentOrder(2).cabin(Cabin.BUSINESS).price(new BigDecimal("350"))
                        .booking(booking).flight(flight).build()
        ));

        // WHEN
        BigDecimal total = bookingItemRepository.calculateTotalPrice(booking.getId());

        // THEN
        assertThat(total).isEqualByComparingTo("600");
    }

    @Test
    @DisplayName("BookingItem: cuenta asientos por vuelo y cabina")
    void shouldCountSeatsByFlightAndCabin() {
        // GIVEN
        var passenger = createPassenger("count@test.com", "Count Tester");
        Booking booking = createBooking(passenger);
        Flight flight = createFlight("F400", "Berlin", "nevado");

        bookingItemRepository.saveAll(List.of(
                BookingItem.builder().segmentOrder(1).cabin(Cabin.ECONOMY).price(new BigDecimal("200"))
                        .booking(booking).flight(flight).build(),
                BookingItem.builder().segmentOrder(2).cabin(Cabin.ECONOMY).price(new BigDecimal("220"))
                        .booking(booking).flight(flight).build(),
                BookingItem.builder().segmentOrder(3).cabin(Cabin.BUSINESS).price(new BigDecimal("500"))
                        .booking(booking).flight(flight).build()
        ));

        // WHEN
        long economyCount = bookingItemRepository.countByFlightIdAndCabin(flight.getId(), Cabin.ECONOMY);
        long businessCount = bookingItemRepository.countByFlightIdAndCabin(flight.getId(), Cabin.BUSINESS);

        // THEN
        assertThat(economyCount).isEqualTo(2);
        assertThat(businessCount).isEqualTo(1);
    }
}


