package unimagdalena.edu.co.Taller1.api.dto;

import jakarta.annotation.Nonnull;

import java.io.Serializable;
import java.math.BigDecimal;

public class BookingItemDtos {

    public record BookingItemCreateRequest(@Nonnull String cabin, @Nonnull BigDecimal price, @Nonnull Integer segmentOrder, Long flightId) implements Serializable {}

    public record BookingItemUpdateRequest(String cabin, BigDecimal price, Integer segmentOrder) implements Serializable {}

    public record BookingItemResponse(Long id, String cabin, BigDecimal price, Integer segmentOrder, Long booking_id, Long flight_id, String flight_number) implements Serializable{}
}
