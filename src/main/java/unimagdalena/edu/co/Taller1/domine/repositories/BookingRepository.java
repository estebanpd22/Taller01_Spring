package unimagdalena.edu.co.Taller1.domine.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unimagdalena.edu.co.Taller1.domine.entities.Booking;

import java.time.OffsetDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    //Pagina las reservas de un pasajero (por email, ignorando case) ordenadas desc
    Page<Booking> findByPassenger_EmailIgnoreCaseOrderByCreatedAtDesc(
            String email,
            Pageable pageable
    );

    //Trae una reserva por id precargando items, items.flight y passenger
    @EntityGraph(attributePaths = {"items", "items.flight", "passenger"})
    @Query("SELECT b FROM Booking b WHERE b.id = :id")
    Booking fetchGraphById(@Param("id") Long id);

    List<Booking> findByCreatedAtBetween(OffsetDateTime start, OffsetDateTime end);
}