package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.AirlineDtos.*;
import unimagdalena.edu.co.Taller1.entities.Airline;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.repositories.AirlineRepository;
import unimagdalena.edu.co.Taller1.services.impl.AirlineServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirlineServiceImplTest {

    @Mock
    private AirlineRepository airlineRepository;

    @InjectMocks
    private AirlineServiceImpl airlineService;

    @Test
    void shouldCreateAirline() {
        var request = new AirlineCreateRequest("av", "Avianca");

        when(airlineRepository.findByCodeIgnoreCase("AV")).thenReturn(Optional.empty());
        when(airlineRepository.save(any(Airline.class))).thenAnswer(inv -> {
            Airline a = inv.getArgument(0);
            a.setId(1L);
            return a;
        });

        var result = airlineService.create(request);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.code()).isEqualTo("AV");
        assertThat(result.name()).isEqualTo("Avianca");

        verify(airlineRepository).findByCodeIgnoreCase("AV");
        verify(airlineRepository).save(any(Airline.class));
    }

    @Test
    void shouldThrowExceptionWhenCreatingDuplicateCode() {
        var request = new AirlineCreateRequest("av", "Avianca");
        var existing = Airline.builder().id(1L).code("AV").name("Avianca").build();

        when(airlineRepository.findByCodeIgnoreCase("AV")).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> airlineService.create(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Airline code already exists");

        verify(airlineRepository).findByCodeIgnoreCase("AV");
        verify(airlineRepository, never()).save(any());
    }

    @Test
    void shouldGetAirlineById() {
        var entity = Airline.builder().id(1L).code("AV").name("Avianca").build();

        when(airlineRepository.findById(1L)).thenReturn(Optional.of(entity));

        var result = airlineService.get(1L);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.code()).isEqualTo("AV");

        verify(airlineRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenAirlineNotFound() {
        when(airlineRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> airlineService.get(999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Airline not found");

        verify(airlineRepository).findById(999L);
    }

    @Test
    void shouldGetAirlineByCode() {
        var entity = Airline.builder().id(1L).code("AV").name("Avianca").build();

        when(airlineRepository.findByCodeIgnoreCase("AV")).thenReturn(Optional.of(entity));

        var result = airlineService.getByCode("av");

        assertThat(result).isNotNull();
        assertThat(result.code()).isEqualTo("AV");

        verify(airlineRepository).findByCodeIgnoreCase("AV");
    }

    @Test
    void shouldUpdateAirline() {
        var entity = Airline.builder().id(1L).code("AV").name("Avianca").build();
        var request = new AirlineUpdateRequest("AV", "Avianca Airlines");

        when(airlineRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(airlineRepository.save(any(Airline.class))).thenAnswer(inv -> inv.getArgument(0));

        var result = airlineService.update(1L, request);

        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Avianca Airlines");

        verify(airlineRepository).findById(1L);
        verify(airlineRepository).save(any(Airline.class));
    }

    @Test
    void shouldDeleteAirline() {
        when(airlineRepository.existsById(1L)).thenReturn(true);

        airlineService.delete(1L);

        verify(airlineRepository).existsById(1L);
        verify(airlineRepository).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentAirline() {
        when(airlineRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> airlineService.delete(999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Airline not found");

        verify(airlineRepository).existsById(999L);
        verify(airlineRepository, never()).deleteById(any());
    }
}

