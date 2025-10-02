package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.BookingDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.*;
import unimagdalena.edu.co.Taller1.domine.repositories.BookingRepository;
import unimagdalena.edu.co.Taller1.domine.repositories.FlightRepository;
import unimagdalena.edu.co.Taller1.domine.repositories.PassengerRepository;
import unimagdalena.edu.co.Taller1.services.impl.BookingServiceImpl;
import unimagdalena.edu.co.Taller1.services.mapperStruct.BookingMapperStruct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {

    @Mock
    BookingRepository bookingRepository;

    @Mock
    PassengerRepository passengerRepository;

    @Mock
    FlightRepository flightRepository;

    @InjectMocks
    BookingServiceImpl bookingService;



    @Test
    void shouldCreateBookingAndMapToResponse(){
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(Passenger.builder().id(1L)
                .fullName("Lionel Messi").email("messi.10GOAT@example.com").build()));
        when(bookingRepository.save(any())).thenAnswer(inv -> {
            Booking b = inv.getArgument(0); b.setId(101L); return b;
        });

        var response = bookingService.create(new BookingCreateRequest(1L));

        assertThat(response.id()).isEqualTo(101L);
        assertThat(response.passenger_name()).isEqualTo("Lionel Messi");
        assertThat(response.passenger_email()).isEqualTo("messi.10GOAT@example.com");
    }

    @Test
    void shouldUpdateBookingAndMapToResponse(){
        var passenger = Optional.of(Passenger.builder().id(1L).fullName("Susana Horia Seca")
                .email("susana.horia44@example.com").build());
        var new_passenger = Optional.of(Passenger.builder().id(2L).fullName("Adolfo Jose Hitler Moreno")
                .email("hitlermoreno@example.com").build());
        when(bookingRepository.findById(101L)).thenReturn(Optional.of(Booking.builder().id(101L)
                .createdAt(OffsetDateTime.now().minusDays(1)).passenger(passenger.get()).build()));
        when(passengerRepository.findById(2L)).thenReturn(new_passenger);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(inv -> inv.getArgument(0));

        var response = bookingService.update(101L, 2L);

        assertThat(response.id()).isEqualTo(101L);
        assertThat(response.passenger_name()).isEqualTo("Adolfo Jose Hitler Moreno");
        assertThat(response.passenger_email()).isEqualTo("hitlermoreno@example.com");

    }

    @Test
    void shouldListBookingsBetweenDates(){
        var now = OffsetDateTime.now();
        when(bookingRepository.findByCreatedAtBetween(now.minusDays(1), now)).thenReturn(List.of(
                Booking.builder().id(1L).createdAt(now.minusHours(12)).build(),
                Booking.builder().id(2L).createdAt(now.minusHours(23)).build(),
                Booking.builder().id(3L).createdAt(now.minusMinutes(5)).build()
        ));

        var response = bookingService.listBookingsBetweenDates(now.minusDays(1), now);

        assertThat(response).hasSize(3);
        assertThat(response).allSatisfy(booking -> assertThat(booking.createdAt())
                .isBetween(now.minusDays(1), now.minusMinutes(5)));
    }

    @Test
    void shouldListBookingsByPassengerEmailAndOrderedByRecentlyCreation(){
        var now = OffsetDateTime.now(); var passenger = Optional.of(Passenger.builder().id(1L)
                .fullName("Cristiano Penaldo").email("elcomediante07@example.com").build());
        when(bookingRepository.findByPassenger_EmailIgnoreCaseOrderByCreatedAtDesc(passenger.get().getEmail(),
                Pageable.unpaged())).thenReturn(new PageImpl<>(
                List.of(Booking.builder().id(3L).createdAt(now.minusMinutes(5)).passenger(passenger.get()).build(),
                        Booking.builder().id(1L).createdAt(now.minusHours(12)).passenger(passenger.get()).build(),
                        Booking.builder().id(2L).createdAt(now.minusHours(23)).passenger(passenger.get()).build()
                )
        ));

        var response = bookingService.listBookingsByPassengerEmailAndOrderedMostRecently(passenger.get().getEmail(),
                Pageable.unpaged());

        assertThat(response).hasSize(3);
        assertThat(response).extracting(BookingResponse::passenger_email)
                .allMatch(email -> email.equals("elcomediante07@example.com"));
    }
    @Test
    void shouldFetchBookingWithItemsAndPassenger() {
        // Arrange - crear Passenger
        Passenger passenger = Passenger.builder()
                .fullName("Carlos Sehuanes")
                .email("carlos@example.com")
                .build();
        passenger = passengerRepository.save(passenger);

        Airline airline = Airline.builder().name("Avianca").code("AV").build();
        Airport origin = Airport.builder().code("SMR").name("Santa Marta").city("Santa Marta").build();
        Airport destination = Airport.builder().code("BOG").name("El Dorado").city("Bogotá").build();

        Flight flight = Flight.builder()
                .number("AV123")
                .departureTime(OffsetDateTime.now())
                .arrivalTime(OffsetDateTime.now().plusHours(1))
                .airline(airline)
                .origin(origin)
                .destination(destination)
                .build();
        flight = flightRepository.save(flight);

        Booking booking = Booking.builder()
                .createdAt(OffsetDateTime.now())
                .passenger(passenger)
                .build();

        BookingItem item = BookingItem.builder()
                .cabin(Cabin.ECONOMY)
                .price(BigDecimal.valueOf(150.00))
                .segmentOrder(1)
                .flight(flight)
                .booking(booking)
                .build();

        booking.addItem(item);

        booking = bookingRepository.save(booking);

        Optional<Booking> resultOpt = bookingRepository.fetchGraphById(booking.getId());

        assertThat(resultOpt).isPresent();
        Booking result = resultOpt.get();

        assertThat(result.getPassenger()).isNotNull();
        assertThat(result.getPassenger().getEmail()).isEqualTo("carlos@example.com");

        assertThat(result.getItems()).hasSize(1);
        BookingItem loadedItem = result.getItems().getFirst();

        assertThat(loadedItem.getFlight()).isNotNull();
        assertThat(loadedItem.getFlight().getNumber()).isEqualTo("AV123");
    }
}
