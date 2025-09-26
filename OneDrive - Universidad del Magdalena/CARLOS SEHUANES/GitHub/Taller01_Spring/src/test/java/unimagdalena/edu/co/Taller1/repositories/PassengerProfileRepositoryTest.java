package unimagdalena.edu.co.Taller1.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import unimagdalena.edu.co.Taller1.domine.entities.Passenger;
import unimagdalena.edu.co.Taller1.domine.entities.PassengerProfile;
import unimagdalena.edu.co.Taller1.domine.repositories.PassengerProfileRepository;
import unimagdalena.edu.co.Taller1.domine.repositories.PassengerRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PassengerProfileRepositoryTest {

    @Autowired
    private PassengerProfileRepository passengerProfileRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    @DisplayName("Debe guardar y recuperar un PassengerProfile con su Passenger")
    void shouldSaveAndFindPassengerProfile() {
        // GIVEN
        Passenger passenger = passengerRepository.save(
                Passenger.builder()
                        .fullName("Juan Perez")
                        .email("juan.perez@example.com")
                        .build()
        );

        PassengerProfile profile = PassengerProfile.builder()
                .phone("123456789")
                .CountryCode("+57")
                .passenger(passenger)
                .build();

        passengerProfileRepository.save(profile);

        // WHEN
        Optional<PassengerProfile> encontrado = passengerProfileRepository.findById(profile.getId());

        // THEN
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getPhone()).isEqualTo("123456789");
        assertThat(encontrado.get().getCountryCode()).isEqualTo("+57");
        assertThat(encontrado.get().getPassenger().getEmail()).isEqualTo("juan.perez@example.com");
    }

    @Test
    @DisplayName("Debe eliminar un PassengerProfile sin borrar el Passenger")
    void shouldDeleteProfileButKeepPassenger() {
        // GIVEN
        Passenger passenger = passengerRepository.save(
                Passenger.builder()
                        .fullName("Ana lopez")
                        .email("ana.lopez@example.com")
                        .build()
        );

        PassengerProfile profile = passengerProfileRepository.save(
                PassengerProfile.builder()
                        .phone("3332288915")
                        .CountryCode("+57")
                        .passenger(passenger)
                        .build()
        );

        // WHEN
        passengerProfileRepository.delete(profile);

        // THEN
        assertThat(passengerProfileRepository.findById(profile.getId())).isNotPresent();
        assertThat(passengerRepository.findById(passenger.getId())).isPresent();
    }
}

