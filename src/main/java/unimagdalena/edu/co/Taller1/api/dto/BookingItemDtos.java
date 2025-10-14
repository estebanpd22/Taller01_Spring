package unimagdalena.edu.co.Taller1.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;
import unimagdalena.edu.co.Taller1.entities.Cabin;

import java.io.Serializable;
import java.math.BigDecimal;

public class BookingItemDtos {
    public record BookingItemCreateRequest(@Min(1) BigDecimal price, @Min(1) Integer segmentOrder, Cabin cabin, @NotNull Long flighId) implements Serializable {}
    public record BookingItemUpdateRequest(BigDecimal price, Integer segmentOrder, Cabin cabin,@Nullable Long flight_id ) implements Serializable {}
    public record BookingItemResponse(Long id, BigDecimal price, Integer segmentOrder, Cabin cabin, FlightDtos.FlightResponse flightDto) implements Serializable {}
}
