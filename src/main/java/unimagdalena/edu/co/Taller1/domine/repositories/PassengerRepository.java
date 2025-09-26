package unimagdalena.edu.co.Taller1.domine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unimagdalena.edu.co.Taller1.domine.entities.Passenger;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger,Long> {
    // Busca un pasajero por email, ignorando mayúsculas/minúsculas
    Optional<Passenger> findByEmailIgnoreCase(String email);

    // JPQL: Busca un pasajero por email e incluye el PassengerProfile (evita N+1)
    @Query("""
           SELECT p FROM Passenger p
           LEFT JOIN FETCH p.profile
           WHERE LOWER(p.email) = LOWER(:email)
           """)
    Optional<Passenger> fetchWithProfileByEmail(@Param("email") String email);
}
