package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.AirlineDtos;
import unimagdalena.edu.co.Taller1.api.dto.AirlineDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Airline;
import unimagdalena.edu.co.Taller1.domine.repositories.AirlineRepository;
import unimagdalena.edu.co.Taller1.services.impl.AirlineServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import unimagdalena.edu.co.Taller1.services.mapperStruct.AirlineMapperStruct;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirlineServiceImplTest {
    @Mock
    AirlineRepository airlineRepository;

    @Mock
    AirlineMapperStruct mapper;

    @InjectMocks
    AirlineServiceImpl airlineService;

    @Test
    void shouldCreateAirlineAndMapToResponse(){
        Airline airlineEntity = Airline.builder()
                .code("XD")
                .name("Despegar.com")
                .build();

        when(mapper.toEntity(any(AirlineCreateRequest.class)))
                .thenReturn(airlineEntity);

        when(airlineRepository.save(any(Airline.class))).thenAnswer(inv -> {
            Airline a = inv.getArgument(0);
            a.setId(1L);
            return a;
        });

        when(mapper.toResponse(any(Airline.class))).thenAnswer(inv -> {
            Airline a = inv.getArgument(0);
            return new AirlineResponse(a.getId(), a.getCode(), a.getName());
        });

        var response = airlineService.create(new AirlineCreateRequest("XD", "Despegar.com"));

        assertThat(response.id()).isNotNull();
        assertThat(response.code()).isEqualTo("XD");
        assertThat(response.name()).isEqualTo("Despegar.com");
    }

    @Test
    void shouldUpdateAirlineAndMapToResponse(){
        when(airlineRepository.findById(1L)).thenReturn(
                Optional.of(Airline.builder().id(1L).code("XD").name("Despegar.com").build())
        );

        doAnswer(inv -> {
            Airline airline = inv.getArgument(1);
            AirlineUpdateRequest request = inv.getArgument(0);
            airline.setCode(request.code());
            airline.setName(request.name());
            return null;
        }).when(mapper).patch(any(AirlineUpdateRequest.class), any(Airline.class));

        when(airlineRepository.save(any(Airline.class))).thenAnswer(inv -> inv.getArgument(0));

        when(mapper.toResponse(any(Airline.class))).thenAnswer(inv -> {
            Airline a = inv.getArgument(0);
            return new AirlineResponse(a.getId(), a.getCode(), a.getName());
        });

        var response = airlineService.update(1L, new AirlineUpdateRequest("XD", "Wingo"));

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.code()).isEqualTo("XD");
        assertThat(response.name()).isEqualTo("Wingo");
    }

    @Test
    void shouldListAllAirlinesInPages(){
        when(airlineRepository.findAll(Pageable.ofSize(4))).thenReturn(new PageImpl<>(List.of(
                Airline.builder().id(1L).code("XD").name("Despegar.com").build(),
                Airline.builder().id(2L).code("DD").name("Wingo").build(),
                Airline.builder().id(3L).code("XL").name("Trivago").build(),
                Airline.builder().id(4L).code("VI").name("LATAM Airlines").build()
        )));

        when(mapper.toResponse(any(Airline.class))).thenAnswer(inv -> {
            Airline a = inv.getArgument(0);
            return new AirlineResponse(a.getId(), a.getCode(), a.getName());
        });

        var response = airlineService.airlinePageList(Pageable.ofSize(4));

        assertThat(response).hasSize(4);
        assertThat(response).extracting(AirlineResponse::id).containsExactlyInAnyOrder(1L, 2L, 3L, 4L);
    }
}

