package unimagdalena.edu.co.Taller1.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import unimagdalena.edu.co.Taller1.domine.entities.Passenger;
import unimagdalena.edu.co.Taller1.domine.entities.PassengerProfile;
import unimagdalena.edu.co.Taller1.domine.repositories.PassengerRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PassengerRepositoryTest {

    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    @DisplayName("Debe encontrar un pasajero por email sin importar mayúsculas/minúsculas")
    void testFindByEmailIgnoreCase() {
        // Arrange
        Passenger passenger = Passenger.builder()
                .fullName("Esteban")
                .email("usuario@test.com")
                .build();
        passengerRepository.save(passenger);

        // Act
        Optional<Passenger> encontrado = passengerRepository.findByEmailIgnoreCase("USUARIO@TEST.COM");

        // Assert
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getFullName()).isEqualTo("Esteban");
    }

    @Test
    @DisplayName("Debe traer un pasajero junto con su perfil")
    void testFetchWithProfileByEmail() {
        // Arrange
        PassengerProfile profile = PassengerProfile.builder()
                .id(null) // autogenerado
                .build();

        Passenger passenger = Passenger.builder()
                .fullName("Carlos")
                .email("carlos@test.com")
                .profile(profile)
                .build();

        passengerRepository.save(passenger);

        // Act
        Optional<Passenger> encontrado = passengerRepository.fetchWithProfileByEmail("carlos@test.com");

        // Assert
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getProfile()).isNotNull();
    }
}

