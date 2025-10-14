package unimagdalena.edu.co.Taller1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unimagdalena.edu.co.Taller1.entities.Airport;

import java.util.List;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport,Long> {
    Optional<Airport> findByCodeIgnoreCase(String code);
    List<Airport> findByCity(String city);
}

