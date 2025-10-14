package unimagdalena.edu.co.Taller1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unimagdalena.edu.co.Taller1.entities.Airline;

import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
    Optional<Airline> findByCodeIgnoreCase(String code);
}

