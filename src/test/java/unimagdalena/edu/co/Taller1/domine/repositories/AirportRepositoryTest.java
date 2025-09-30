package unimagdalena.edu.co.Taller1.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import unimagdalena.edu.co.Taller1.domine.entities.Airport;
import unimagdalena.edu.co.Taller1.domine.repositories.AirportRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AirportRepositoryTest extends AbstractRepositoryTI {

    @Autowired
    private AirportRepository airportRepository;

    @Test
    @DisplayName("Debe encontrar aeropuerto por código ignorando mayúsculas/minúsculas")
    void shouldFindByCodeIgnoreCase() {
        // GIVEN
        Airport bog = Airport.builder()
                .code("BOG")
                .name("El Dorado")
                .city("Bogotá")
                .build();
        airportRepository.save(bog);

        // WHEN
        Optional<Airport> encontrado = airportRepository.findByCodeIgnoreCase("bog");

        // THEN
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getId()).isNotNull();
        assertThat(encontrado.get().getName()).isEqualTo("El Dorado");
        assertThat(encontrado.get().getCity()).isEqualTo("Bogotá");
    }

}

