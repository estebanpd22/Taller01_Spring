package unimagdalena.edu.co.Taller1.services;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import unimagdalena.edu.co.Taller1.api.dto.AirlineDtos;
import unimagdalena.edu.co.Taller1.api.dto.AirportDtos;
import unimagdalena.edu.co.Taller1.api.dto.AirportDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Airline;
import unimagdalena.edu.co.Taller1.domine.entities.Airport;
import unimagdalena.edu.co.Taller1.domine.repositories.AirportRepository;
import unimagdalena.edu.co.Taller1.services.impl.AirportServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import unimagdalena.edu.co.Taller1.services.mapperStruct.AirportMapperStruct;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirportServiceImplTest {
    @Mock
    AirportRepository airportRepository;

    @Mock
    AirportMapperStruct mapperStruct;

    @InjectMocks
    AirportServiceImpl airportService;

    @Test
    void shouldCreateAirportAndMapToResponse(){
        Airport airport = Airport.builder()
                .code("Code")
                .name("Nombre")
                .city("City")
                .build();

        when(mapperStruct.toEntity(any(AirportCreateRequest.class))).thenReturn(airport);


        when(airportRepository.save(any(Airport.class))).thenAnswer(inv -> {
            Airport a = inv.getArgument(0);
            a.setId(11L);
            return a;
        });

        when(mapperStruct.toResponse(any(Airport.class))).thenAnswer(inv -> {
            Airport a = inv.getArgument(0);
            return new AirportResponse(a.getId(), a.getCode(), a.getName(), a.getCity());
        });

        var response = airportService.create(new AirportCreateRequest("Code", "Nombre", "City"));

        assertThat(response.id()).isNotNull();
        assertThat(response.code()).isEqualTo("Code");
        assertThat(response.name()).isEqualTo("Nombre");
        assertThat(response.city()).isEqualTo("City");
    }

    @Test
    void shouldUpdateAirportAndMapToResponse(){
        when(airportRepository.findById(11L)).thenReturn(
                Optional.of(Airport.builder().id(11L).code("code")
                .name("name").city("city").build()));

        doAnswer(inv ->{
            Airport airport = inv.getArgument(0);
            AirportDtos.AirportUpdateRequest request = inv.getArgument(1);
            airport.setCode(request.code());
            airport.setName(request.name());
            return null;
        }).when(mapperStruct).patch(any(Airport.class), any(AirportDtos.AirportUpdateRequest.class));

        when(airportRepository.save(any(Airport.class))).thenAnswer(inv -> inv.getArgument(0));

        when(mapperStruct.toResponse(any(Airport.class))).thenAnswer(inv -> {
            Airport a = inv.getArgument(0);
            return new AirportResponse(a.getId(), a.getCode(), a.getName(), a.getCity());
        });

        var response = airportService.update(1L, new AirportUpdateRequest("code", "name"));

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.code()).isEqualTo("code");
        assertThat(response.name()).isEqualTo("name");
        assertThat(response.city()).isEqualTo("city");
    }

    @Test
    void shouldListCityAirports(){
        when(airportRepository.findAll(Pageable.ofSize(4))).thenReturn(new PageImpl<>(List.of(
                Airport.builder().id(1L).code("1").name("airport1").city("city1").build(),
                Airport.builder().id(2L).code("2").name("airport2").city("city2").build(),
                Airport.builder().id(3L).code("3").name("airport3").city("city3").build(),
                Airport.builder().id(4L).code("4").name("airport4").city("city4").build()
        )));

        when(mapperStruct.toResponse(any(Airport.class))).thenAnswer(inv -> {
            Airport a = inv.getArgument(0);
            return new AirportResponse(a.getId(), a.getCode(), a.getName(), a.getCity());
        });

        var response = airportService.airportList(Pageable.ofSize(4));

        assertThat(response).hasSize(4);
        assertThat(response).extracting(AirportDtos.AirportResponse::id).containsExactlyInAnyOrder(1L, 2L, 3L, 4L);
        }
}
