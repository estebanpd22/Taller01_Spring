package unimagdalena.edu.co.Taller1.domine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unimagdalena.edu.co.Taller1.domine.entities.SeatInventory;
import unimagdalena.edu.co.Taller1.domine.entities.Cabin;

import java.util.Optional;

public interface SeatInventoryRepository extends JpaRepository<SeatInventory,Long> {
    // Retorna el inventario de asientos para una cabina específica de un vuelo
    Optional<SeatInventory> findByFlight_IdAndCabinType(Long flightId, Cabin cabin);

    //Verifica si availableSeats >= min
    @Query("""
           SELECT CASE WHEN si.totalSeats >= :min THEN TRUE ELSE FALSE END
           FROM SeatInventory si
           WHERE si.flight.id = :flightId
             AND si.cabin = :cabin
           """)
    boolean hasAvailableSeats(@Param("flightId") Long flightId,
                              @Param("cabin") Cabin cabin,
                              @Param("min") Integer min);
}