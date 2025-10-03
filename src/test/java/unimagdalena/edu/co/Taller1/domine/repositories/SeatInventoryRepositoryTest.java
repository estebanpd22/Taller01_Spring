package unimagdalena.edu.co.Taller1.domine.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import unimagdalena.edu.co.Taller1.domine.entities.SeatInventory;
import unimagdalena.edu.co.Taller1.domine.entities.Flight;
import unimagdalena.edu.co.Taller1.domine.entities.Cabin;
import unimagdalena.edu.co.Taller1.domine.repositories.FlightRepository;
import unimagdalena.edu.co.Taller1.domine.repositories.SeatInventoryRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class SeatInventoryRepositoryTest extends AbstractRepositoryTI {

    @Autowired
    private SeatInventoryRepository seatInventoryRepository;

    @Autowired
    private FlightRepository flightRepository;

    // --- Helpers ---
    private Flight createFlight(String number) {
        return flightRepository.save(
                Flight.builder()
                        .number(number)
                        .build()
        );
    }

    private void createSeatInventory(Flight flight, Cabin cabin, int totalSeats, int availableSeats) {
        seatInventoryRepository.save(
                SeatInventory.builder()
                        .flight(flight)
                        .cabin(cabin)
                        .totalSeats(totalSeats)
                        .availableSeats(availableSeats)
                        .build()
        );
    }

    // --- Tests ---
    @Test
    @DisplayName("Debe encontrar inventario por vuelo y cabina")
    void shouldFindInventoryByFlightAndCabin() {
        // GIVEN
        Flight flight = createFlight("AV123");
        createSeatInventory(flight, Cabin.ECONOMY, 50, 45);

        // WHEN
        Optional<SeatInventory> encontrado =
                seatInventoryRepository.findByFlight_IdAndCabin(flight.getId(), Cabin.ECONOMY);

        // THEN
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getTotalSeats()).isEqualTo(50);
        assertThat(encontrado.get().getAvailableSeats()).isEqualTo(45);
    }

    @Test
    @DisplayName("Debe verificar si existen asientos disponibles")
    void shouldCheckAvailableSeats() {
        // GIVEN
        Flight flight = createFlight("LA456");
        createSeatInventory(flight, Cabin.BUSINESS, 10, 5);

        // WHEN
        boolean disponible = seatInventoryRepository.hasAvailableSeats(flight.getId(), Cabin.BUSINESS, 3);
        boolean noDisponible = seatInventoryRepository.hasAvailableSeats(flight.getId(), Cabin.BUSINESS, 8);

        // THEN
        assertThat(disponible).isTrue();
        assertThat(noDisponible).isFalse();
    }
}


