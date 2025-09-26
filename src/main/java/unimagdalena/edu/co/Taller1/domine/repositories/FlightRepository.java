package unimagdalena.edu.co.Taller1.domine.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unimagdalena.edu.co.Taller1.domine.entities.Flight;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight,Long> {
    // Retorna vuelos operados por la misma aerolinea
    List<Flight> findByAirline_Name(String name);

    //Retorna vuelos por origen, destino y ventana de salida; con paginación
    Page<Flight> findByOrigin_CodeAndDestination_CodeAndDepartureTimeBetween(
            String origin,
            String destination,
            LocalDateTime departureTime,
            Pageable pageable);

    //Filtra por origen/destino (opcionales) y ventana de salida, precargando airline, origin,
    @EntityGraph(attributePaths = {"airline", "origin", "destination", "tags"})
    @Query("""
           SELECT f FROM Flight f
           WHERE (:o IS NULL OR f.origin.code = :o)
             AND (:d IS NULL OR f.destination.code = :d)
             AND f.departureTime BETWEEN :from AND :to
           """)
    List<Flight> searchWithAssociations(@Param("o") String origin,
                                        @Param("d") String destination,
                                        @Param("from") OffsetDateTime from,
                                        @Param("to") OffsetDateTime to);

    //Retorna vuelos que tienen TODAS las tags dadas
    @Query(value = """
           SELECT f.* FROM flights f
           JOIN flight_tags ft ON ft.flight_id = f.id
           JOIN tags t ON t.id = ft.tag_id
           WHERE t.name IN (:tags)
           GROUP BY f.id
           HAVING COUNT(DISTINCT t.name) = :requiredCount
           """, nativeQuery = true)
    List<Flight> findFlightsWithAllTags(@Param("tags") Collection<String> tags,
                                        @Param("requiredCount") long requiredCount);

}
