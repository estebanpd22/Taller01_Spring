package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.AirportDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Airport;
import unimagdalena.edu.co.Taller1.domine.repositories.AirportRepository;
import unimagdalena.edu.co.Taller1.services.impl.AirportServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirportServiceImplTest {
    @Mock
    AirportRepository airportRepository;

    @InjectMocks
    AirportServiceImpl airportService;

    @Test
    void shouldCreateAirportAndMapToResponse(){
        when(airportRepository.save(any())).thenAnswer(inv -> {
            Airport a = inv.getArgument(0); a.setId(11L); return a;
        });

        var response = airportService.create(new AirportCreateRequest("ZZZ", "RandomAirport", "Malambo"));

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(11L);
        assertThat(response.code()).isEqualTo("ZZZ");
        assertThat(response.name()).isEqualTo("RandomAirport");
    }

    @Test
    void shouldUpdateAirportAndMapToResponse(){
        when(airportRepository.findById(11L)).thenReturn(Optional.of(Airport.builder().id(11L).code("ZZZ")
                .name("RandomAirport").city("Malambo").build()));
        when(airportRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var response = airportService.update(11L, new AirportUpdateRequest(null, "NoSeMeOcurreNada"));

        assertThat(response.id()).isEqualTo(11L);
        assertThat(response.code()).isEqualTo("ZZZ");
        assertThat(response.name()).isEqualTo("NoSeMeOcurreNada");
    }

    @Test
    void shouldListCityAirports(){
        var city = "Soledad";
        when(airportRepository.findByCity(city)).thenReturn(List.of(Airport.builder().id(11L).code("ZZZ").name("Wipiiii").city(city).build(),
                Airport.builder().id(12L).code("XXD").name("Imaginacion't").city(city).build(),
                Airport.builder().id(14L).code("XYZ").name("Aynooo").city(city).build()));

        var response = airportService.getCityList(city);
        assertThat(response).hasSize(3);
        assertThat(response).extracting(AirportResponse::city).allMatch(c -> c.equals(city));
    }
}
