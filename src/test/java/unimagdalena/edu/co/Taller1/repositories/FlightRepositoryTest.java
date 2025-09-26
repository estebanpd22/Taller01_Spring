package unimagdalena.edu.co.Taller1.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import unimagdalena.edu.co.Taller1.domine.entities.*;
import unimagdalena.edu.co.Taller1.domine.repositories.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class FlightRepositoryTest extends AbstractRepositoryTI {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private TagRepository tagRepository;

    // --- Helpers ---
    private Airport createAirport(String code) {
        return airportRepository.save(
                Airport.builder().code(code).name(code + " Airport").build()
        );
    }

    private Airline createAirline(String name) {
        return airlineRepository.save(Airline.builder().name(name).build());
    }

    private Tag createTag(String name) {
        return tagRepository.save(Tag.builder().name(name).build());
    }

    private Flight createFlight(String number, Airline airline, Airport origin,
                                Airport destination,
                                OffsetDateTime departureTime
    ) {
        return flightRepository.save(
                Flight.builder()
                        .number(number)
                        .airline(airline)
                        .origin(origin)
                        .destination(destination)
                        .departureTime(departureTime)
                        .build()
        );
    }

    // --- Tests ---

    @Test
    @DisplayName("Debe encontrar vuelos por nombre de la aerolínea")
    void testFindByAirlineName() {
        // GIVEN
        Airline airline = createAirline("Avianca");
        Airport origin = createAirport("BOG");
        Airport destination = createAirport("MDE");

        Flight flight = createFlight("AV123", airline, origin, destination, OffsetDateTime.now().plusDays(1));

        // WHEN
        List<Flight> flights = flightRepository.findByAirline_Name("Avianca");

        // THEN
        assertThat(flights).isNotEmpty();
        assertThat(flights).allMatch(f -> f.getAirline().getName().equalsIgnoreCase("Avianca"));
    }

    @Test
    @DisplayName("Debe paginar vuelos por origen, destino y ventana de tiempo")
    void shouldFindFlightsByRouteAndTimeWindow() {
        // GIVEN
        Airport bog = createAirport("BOG");
        Airport mde = createAirport("MDE");
        Airline avianca = createAirline("Avianca");

        createFlight("AV300", avianca, bog, mde, OffsetDateTime.now().plusHours(5));
        createFlight("AV301", avianca, bog, mde, OffsetDateTime.now().plusHours(10));

        PageRequest pageable = PageRequest.of(0, 1);

        // WHEN
        Page<Flight> flights = flightRepository.findByOrigin_CodeAndDestination_CodeAndDepartureTimeBetween(
                "BOG", "MDE",
                OffsetDateTime.now(), OffsetDateTime.now().plusDays(1),
                pageable
        );

        // THEN
        assertThat(flights.getContent()).hasSize(1);
        assertThat(flights.getTotalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("Debe buscar con asociaciones filtrando origen/destino y ventana de tiempo")
    void shouldSearchWithAssociations() {
        // GIVEN
        Airport bog = createAirport("BOG");
        Airport mde = createAirport("MDE");
        Airline avianca = createAirline("Avianca");

        Flight f1 = createFlight("AV400", avianca, bog, mde, OffsetDateTime.now().plusHours(3));
        Flight f2 = createFlight("AV401", avianca, bog, mde, OffsetDateTime.now().plusHours(6));

        // WHEN
        List<Flight> flights = flightRepository.searchWithAssociations(
                "BOG", "MDE",
                OffsetDateTime.now(), OffsetDateTime.now().plusDays(1)
        );

        // THEN
        assertThat(flights).containsExactlyInAnyOrder(f1, f2);
        assertThat(flights.get(0).getAirline()).isNotNull();
        assertThat(flights.get(0).getOrigin()).isNotNull();
        assertThat(flights.get(0).getDestination()).isNotNull();
        assertThat(flights.get(0).getTags()).isNotNull();
    }

    @Test
    @DisplayName("Debe encontrar vuelos que tengan todas las tags dadas")
    void shouldFindFlightsWithAllTags() {
        // GIVEN
        Airport bog = createAirport("BOG");
        Airport mde = createAirport("MDE");
        Airline avianca = createAirline("Avianca");

        Tag promo = createTag("PROMO");
        Tag vip = createTag("VIP");

        Flight f1 = createFlight("AV500", avianca, bog, mde, OffsetDateTime.now().plusDays(1));
        f1.setTags(Set.of(promo, vip));
        flightRepository.save(f1);

        Flight f2 = createFlight("AV501", avianca, bog, mde, OffsetDateTime.now().plusDays(1));
        f2.setTags(Set.of(promo));
        flightRepository.save(f2);

        // WHEN
        List<Flight> flights = flightRepository.findFlightsWithAllTags(
                List.of("PROMO", "VIP"), 2
        );

        // THEN
        assertThat(flights).containsExactly(f1);
        assertThat(flights).doesNotContain(f2);
    }
}
