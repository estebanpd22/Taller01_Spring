package unimagdalena.edu.co.Taller1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unimagdalena.edu.co.Taller1.entities.BookingItem;
import unimagdalena.edu.co.Taller1.entities.Cabin;

import java.math.BigDecimal;
import java.util.List;

public interface BookingItemRepository extends JpaRepository<BookingItem,Long> {
    //Lista los segmentos de una reserva en orden por segmentOrder
    List<BookingItem> findByBooking_IdOrderBySegmentOrder(Long bookingId);

    //Calcula el total de la reserva sumando los precios de sus ítems
    @Query("""
           SELECT COALESCE(SUM(bi.price), 0)
           FROM BookingItem bi
           WHERE bi.booking.id = :bookingId
           """)
    BigDecimal calculateTotalPrice(@Param("bookingId") Long bookingId);

    //Cuenta cuántos asientos han sido comprados / reservados para un vuelo y cabina
    @Query("""
           SELECT COUNT(bi)
           FROM BookingItem bi
           WHERE bi.flight.id = :flightId
             AND bi.cabin = :cabin
           """)
    long countSeatsByFlightAndCabin(@Param("flightId") Long flightId,
                                    @Param("cabin") Cabin cabin);

}
