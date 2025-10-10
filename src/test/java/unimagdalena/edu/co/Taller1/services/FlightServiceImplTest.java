package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.FlightDtos;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos.*;
import unimagdalena.edu.co.Taller1.api.dto.TagDtos.TagResponse;
import unimagdalena.edu.co.Taller1.domine.entities.*;
import unimagdalena.edu.co.Taller1.domine.repositories.*;
import unimagdalena.edu.co.Taller1.services.impl.FlightServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import unimagdalena.edu.co.Taller1.services.mapperStruct.FlightMapperStruct;

import javax.lang.model.util.Elements;

import static org.assertj.core.api.Assertions.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class FlightServiceImplTest {
    @Mock FlightRepository flightRepository;
    @Mock AirportRepository airportRepository;
    @Mock AirlineRepository airlineRepository;
    @Mock TagRepository tagRepository;

    @Mock
    FlightMapperStruct mapperStruct;

    @InjectMocks FlightServiceImpl flightService;

    @Test
    void shouldCreateFlightAndMapToResponse() {
        Set<Long> tags = new HashSet<>();
        tags.add(1L);
        tags.add(2L);

        Set<TagRef> tagRefs = new HashSet<>();
        tagRefs.add(new TagRef(1L, "tagRef1"));
        tagRefs.add(new TagRef(2L, "tagRef2"));

        var departureTime = OffsetDateTime.now();
        var arrivalTime = OffsetDateTime.now().plusHours(2);

        Flight flight = Flight.builder()
                .number("123")
                .departureTime(departureTime)
                .arrivalTime(arrivalTime)
                .airline(Airline.builder().id(1L).code("airlineCode").name("airlineName").build())
                .origin(Airport.builder().id(2L).code("originCode").name("originName").city("originCity").build())
                .destination(Airport.builder().id(3L).code("destinationCode").name("destinationName").city("destinationCity").build())
                .build();

        when(mapperStruct.toEntity(any(FlightCreateRequest.class))).thenReturn(flight);


        when(flightRepository.save(any(Flight.class))).thenAnswer(invocation -> {
            Flight flight1 =  invocation.getArgument(0);
            flight1.setId(10L);
            return flight1;
        });

        when(mapperStruct.toResponse(any(Flight.class))).thenAnswer(inv -> {
            Flight flight1 =  inv.getArgument(0);
            return new FlightResponse(flight1.getId(), flight1.getNumber(),
                    flight1.getDepartureTime(), flight1.getArrivalTime(),
                    new AirlineRef(flight1.getAirline().getId(), flight1.getAirline().getCode(), flight1.getAirline().getName()),
                    new AirportRef(flight1.getOrigin().getId(), flight1.getOrigin().getCode(), flight1.getOrigin().getCity()),
                    new AirportRef(flight1.getDestination().getId(), flight1.getDestination().getCode(), flight1.getDestination().getCity()),
                    tagRefs);
        });

        var response = flightService.create(new FlightCreateRequest("123", departureTime, arrivalTime, 1L, 2L, 3L, Set.of(1L, 2L)));

        assertThat(response.id()).isNotNull();
        assertThat(response.number()).isEqualTo("123");
        assertThat(response.airline().id()).isEqualTo(1L);
        assertThat(response.origin().id()).isEqualTo(2L);
        assertThat(response.destination().id()).isEqualTo(3L);
        assertThat(response.tags().size()).isEqualTo(2);
    }

    @Test
    void shouldUpdateFlightAndMapToResponse() {
        Set<Long> tags = new HashSet<>();
        tags.add(1L);
        tags.add(2L);

        Set<TagRef> tagRefs = new HashSet<>();
        tagRefs.add(new TagRef(1L, "tagRef1"));
        tagRefs.add(new TagRef(2L, "tagRef2"));

        OffsetDateTime departureTime = OffsetDateTime.now();
        OffsetDateTime arrivalTime = OffsetDateTime.now().plusHours(2);

        Airline mockAirline = Airline.builder().id(1L).code("airlineCode").name("airlineName").build();
        Airport mockOrigin = Airport.builder().id(2L).code("originCode").name("originName").city("originCity").build();
        Airport mockDestination = Airport.builder().id(3L).code("destinationCode").name("destinationName").city("destinationCity").build();
        Set<Tag> mockTags = Set.of(
                Tag.builder().id(1L).name("tagRef1").build(),
                Tag.builder().id(2L).name("tagRef2").build()
        );

        when(airlineRepository.findById(1L)).thenReturn(Optional.of(mockAirline));
        when(airportRepository.findById(2L)).thenReturn(Optional.of(mockOrigin));
        when(airportRepository.findById(3L)).thenReturn(Optional.of(mockDestination));
        when(tagRepository.findTagsByIdIn(Set.of(1L, 2L))).thenReturn(mockTags);

        when(flightRepository.findById(10L)).thenReturn(Optional.of(Flight.builder()
                .id(10L).departureTime(departureTime).arrivalTime(arrivalTime)
                .airline(Airline.builder().code("airlineCode").name("airlineCode").build())
                .origin(Airport.builder().code("originCode").name("originName").city("originCity").build())
                .destination(Airport.builder().code("destinationCode").name("destinationName").city("destinationCity").build())
                .build()));

        doAnswer(inv ->{
            Flight flight = inv.getArgument(0);
            FlightUpdateRequest flightUpdateRequest = inv.getArgument(1);
            flight.setNumber(flightUpdateRequest.number());
            flight.setDepartureTime(flightUpdateRequest.departureTime());
            flight.setArrivalTime(flightUpdateRequest.arrivalTime());
            flight.setAirline(airlineRepository.findById(flightUpdateRequest.airlineId()).get());
            flight.setOrigin(airportRepository.findById(flightUpdateRequest.originId()).get());
            flight.setDestination(airportRepository.findById(flightUpdateRequest.destinationId()).get());
            flight.setTags(tagRepository.findTagsByIdIn(flightUpdateRequest.tagIds()));
            return null;
        }).when(mapperStruct).patch(any(Flight.class), any(FlightUpdateRequest.class));

        when(flightRepository.save(any(Flight.class))).thenAnswer(inv -> {
            return inv.getArgument(0);
        });

        when(mapperStruct.toResponse(any(Flight.class))).thenAnswer(inv -> {
            Flight flight = inv.getArgument(0);
            return new FlightResponse(flight.getId(), flight.getNumber(),
                    flight.getDepartureTime(), flight.getArrivalTime(),
                    new AirlineRef(flight.getAirline().getId(), flight.getAirline().getCode(), flight.getAirline().getName()),
                    new AirportRef(flight.getOrigin().getId(), flight.getOrigin().getCode(), flight.getOrigin().getCity()),
                    new AirportRef(flight.getDestination().getId(), flight.getDestination().getCode(), flight.getDestination().getCity()),
                    tagRefs);

        });

        var response = flightService.update(new FlightUpdateRequest("123",
                departureTime, arrivalTime, 1L, 2L,
                3L, Set.of(1L,2L)), 10L);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(10L);
        assertThat(response.number()).isEqualTo("123");
        assertThat(response.airline().id()).isEqualTo(1L);
        assertThat(response.origin().id()).isEqualTo(2L);
        assertThat(response.destination().id()).isEqualTo(3L);
        assertThat(response.tags().size()).isEqualTo(2);
    }

    //FALTA HACER LOS TEST DESDE ESTE HACIA ABAJO
    @Test
    void shouldAddTagToFlightAndMapToResponse() {
        OffsetDateTime departureTime = OffsetDateTime.now();
        OffsetDateTime arrivalTime = OffsetDateTime.now().plusHours(2);

        var flight = Optional.of(Flight.builder().id(10L).number("123")
                .departureTime(departureTime).arrivalTime(arrivalTime)
                .airline(Airline.builder().id(1L).code("airlineCode").name("airlineName").build())
                .origin(Airport.builder().id(2L).code("originCode").name("originName").city("originCity").build())
                .destination(Airport.builder().id(3L).code("destinationCode").name("destinationName").city("destinationCity").build()).build());

        when(flightRepository.findById(10L));

        /*
        Flight flight = Optional.of(Flight.builder().id(1L).number("XD0001").departureTime(now).arrivalTime(now.plusHours(7)).build());
        when(flightRepository.findById(1L)).thenReturn(flight);
        when(tagRepository.findById(10001L)).thenReturn(Optional.of(Tag.builder().id(10001L).name("tag 1").build()));

        var response = flightService.addTagToFlight(1L, 10001L);

        assertThat(response.tags()).hasSize(3);
*/
    }

    @Test
    void shouldRemoveTagFromFlightAndMapToResponse() {
        var now = OffsetDateTime.now();
        var flight = Optional.of(Flight.builder().id(1L).number("XD0001").departureTime(now).arrivalTime(now.plusHours(7)).build());
        when(flightRepository.findById(1L)).thenReturn(flight);
        when(tagRepository.findById(10001L)).thenReturn(Optional.of(Tag.builder().id(10001L).name("tag 1").build()));

        flightService.addTagToFlight(1L, 10001L);

        var response = flightService.removeTagFromFlight(1L, 10001L);

        assertThat(response.tags()).isEmpty();
    }

    @Test
    void shouldListFlightsByAirline(){
        var airline = Optional.of(Airline.builder().id(1L).code("XD").name("DownAirline").build());
        when(airlineRepository.findById(1L)).thenReturn(airline);
        when(flightRepository.findByAirline_Name("DownAirline", Pageable.unpaged())).thenReturn(new PageImpl<>(List.of(
                Flight.builder().id(101L).number("XD0001").airline(airline.get()).build(),
                Flight.builder().id(102L).number("XD0002").airline(airline.get()).build(),
                Flight.builder().id(103L).number("XD0003").airline(airline.get()).build()
        )));

        var response = flightService.listFlightsByAirline(1L, Pageable.unpaged());

        assertThat(response).hasSize(1);
        assertThat(response).extracting(FlightResponse::airline).isNotNull();

    }

    @Test
    void shouldListScheduledFlights(){
        var origin = Optional.of(Airport.builder().id(1L).code("XD").name("Origin").build());
        var destination = Optional.of(Airport.builder().id(2L).code("DX").name("Final Destination").build());
        var destination_2 = Optional.of(Airport.builder().id(3L).code("ZY").name("Final Destination 2").build());

        var now = OffsetDateTime.now(); var dep_from_time_1 = now.minusHours(12); var dep_to_time_1 = now.plusHours(12);
        var dep_from_time_2 = now.plusHours(2); var dep_to_time_2 = now.plusDays(1).plusHours(12);

        var f1 = Flight.builder().id(101L).origin(origin.get()).destination(destination.get()).departureTime(now).arrivalTime(now.plusHours(5)).build();
        var f2 = Flight.builder().id(102L).origin(origin.get()).destination(destination.get()).departureTime(now.plusHours(4)).arrivalTime(now.plusDays(1)).build();
        var f3 = Flight.builder().id(103L).origin(origin.get()).destination(destination_2.get()).departureTime(now.plusDays(1))
                .arrivalTime(now.plusDays(2).plusHours(12)).build();

        when(airportRepository.findById(1L)).thenReturn(origin);
        when(airportRepository.findById(2L)).thenReturn(destination);
        when(flightRepository.findByOrigin_CodeAndDestination_CodeAndDepartureTimeBetween(origin.get().getCode(), destination.get().getCode(),
                dep_from_time_1, dep_to_time_1, Pageable.unpaged())).thenReturn(new PageImpl<>(List.of(f1, f2)));
        when(flightRepository.filterByOriginAndDestinationOptionalAndDepartureTimeBetween(origin.get().getCode(), null, dep_from_time_2,
                dep_to_time_2)).thenReturn(List.of(f2, f3));

        var response_1 = flightService.listScheduledFlights(1L, 2L, dep_from_time_1, dep_to_time_1, Pageable.unpaged());
        var response_2 = flightService.listScheduledFlights(1L, null, dep_from_time_2, dep_to_time_2, Pageable.unpaged());

        //Response 1
        assertThat(response_1).hasSize(2);
        assertThat(response_1).extracting(FlightResponse::origin).isNotNull();
        assertThat(response_1).extracting(FlightResponse::destination).isNotNull();
        assertThat(response_1).allSatisfy(flight -> assertThat(flight.departureTime()).isBetween(dep_from_time_1, dep_to_time_1));

        //Response 2
        assertThat(response_2).hasSize(2);
        assertThat(response_2).extracting(FlightResponse::origin).isNotNull();
        assertThat(response_1).allSatisfy(flight -> assertThat(flight.departureTime()).isBetween(dep_from_time_2, dep_to_time_2));
    }
}
