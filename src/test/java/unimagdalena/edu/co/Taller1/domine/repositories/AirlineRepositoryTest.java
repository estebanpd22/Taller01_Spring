package unimagdalena.edu.co.Taller1.domine.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import unimagdalena.edu.co.Taller1.domine.entities.Airline;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration
class AirlineRepositoryTest extends AbstractRepositoryTI {

    @Autowired
     AirlineRepository airlineRepository;

    @Test
    @DisplayName("Debe encontrar aerolínea por código ignorando mayúsculas/minúsculas")
    void shouldFindByCodeIgnoreCase() {
        // GIVEN
        var avianca = airlineRepository.save(Airline.builder()
                .code("AV")
                .name("Avianca")
                .build());

        // WHEN
        Optional<Airline> encontrado = airlineRepository.findByCodeIgnoreCase("av");

        // THEN
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getId()).isNotNull();
        assertThat(encontrado.get().getName()).isEqualTo("Avianca");
        assertThat(encontrado.get().getCode()).isEqualTo("AV");
    }

}
