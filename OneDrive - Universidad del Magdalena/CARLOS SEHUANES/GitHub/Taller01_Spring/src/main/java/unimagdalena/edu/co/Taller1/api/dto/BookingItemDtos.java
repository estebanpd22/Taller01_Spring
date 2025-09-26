package unimagdalena.edu.co.Taller1.api.dto;

import jakarta.annotation.Nullable;
import unimagdalena.edu.co.Taller1.domine.entities.Cabin;

import java.io.Serializable;
import java.math.BigDecimal;

public class BookingItemDtos {
    public record BookingItemCreateRequest(BigDecimal price, Integer segmentOrder, Cabin cabin,Long bookngId,Long flighId) implements Serializable {}
    public record BookingItemUpdateRequest(Long id, BigDecimal price, Integer segmentOrder, Cabin cabin, @Nullable Long flight_id ) implements Serializable {}
    public record BookingItemResponse(Long id, BigDecimal price, Integer segmentOrder, Cabin cabin, BookingDtos.BookingResponse bookingDto, FlightDtos.FlightResponse flightDto) implements Serializable {}
}
