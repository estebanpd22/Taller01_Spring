package unimagdalena.edu.co.Taller1.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import unimagdalena.edu.co.Taller1.domine.entities.Passenger;
import unimagdalena.edu.co.Taller1.domine.entities.PassengerProfile;
import unimagdalena.edu.co.Taller1.domine.repositories.PassengerRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PassengerRepositoryTest extends AbstractRepositoryTI {

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
        Passenger passenger = Passenger.builder()
                .fullName("Carlos")
                .email("carlos@test.com")
                .build();

        PassengerProfile profile = PassengerProfile.builder()
                .phone("123456789")
                .countryCode("CO")
                .passenger(passenger)   // 🔹 relación lado dueño
                .build();

        passenger.setProfile(profile); // 🔹 relación lado inverso

        passengerRepository.save(passenger);

        // Act
        Optional<Passenger> encontrado = passengerRepository.fetchWithProfileByEmail("carlos@test.com");

        // Assert
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getProfile()).isNotNull();
        assertThat(encontrado.get().getProfile().getPhone()).isEqualTo("123456789");
    }
}

