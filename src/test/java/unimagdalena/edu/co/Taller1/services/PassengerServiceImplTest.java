package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos;
import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos.*;
import unimagdalena.edu.co.Taller1.entities.Passenger;
import unimagdalena.edu.co.Taller1.entities.PassengerProfile;
import unimagdalena.edu.co.Taller1.repositories.PassengerRepository;
import unimagdalena.edu.co.Taller1.services.impl.PassengerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceImplTest {
    @Mock
    PassengerRepository repository;
    @InjectMocks
    PassengerServiceImpl service;

    @Test
    void shouldCreatePassengerAndReturnPassengerResponse() {
        var request = new PassengerCreateRequest("Sehuanes Carlito","sehuanesemarika@gmail.com",new PassengerDtos.PassengerProfileDto("300554","57"));
        when(repository.save(any(Passenger.class))).thenAnswer(inv -> {
            Passenger p = inv.getArgument(0);
            p.setId(11L);
            return p;
        });

        var response= service.createPassenger(request);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(11L);
        assertThat(response.email()).isEqualTo("sehuanesemarika@gmail.com");
        verify(repository).save(any(Passenger.class));
    }

    @Test
    void shouldUpdatePassengerAndReturnPassengerResponse() {
        var entity = Passenger.builder().id(8L).fullName("Esteban").email("estebancamilop22@gmail.com")
                .profile(PassengerProfile.builder().countryCode("1").phone("3332288915").build()).build();
        when(repository.findById(8L)).thenReturn(Optional.of(entity));
        when(repository.save(any(Passenger.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var passengerUpdateRequest = new PassengerUpdateRequest("Esteban", "puellito22@gmail.com",new PassengerDtos.PassengerProfileDto("300554","57"));

        var passengerUpdateResponse= service.updatePassenger(8L,passengerUpdateRequest);

        assertThat(passengerUpdateResponse.fullname()).isEqualTo("Esteban");
        assertThat(passengerUpdateResponse.profileDto().countryCode()).isEqualTo("57");
    }
}
