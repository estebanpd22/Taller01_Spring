package unimagdalena.edu.co.Taller1.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import unimagdalena.edu.co.Taller1.entities.Cabin;

import java.io.Serializable;

public class SeatInventoryDtos {
    public record SeatInventoryCreateRequest(Cabin cabin, @Min(1) Integer totalSeats, @Min(0) Integer availableSeats, @NotNull Long flightId) implements Serializable {}
    public record SeatInventoryUpdateRequest(Cabin cabin, Integer totalSeats, Integer availableSeats, Long flightId) implements Serializable {}
    public record SeatInventoryResponse(Long id, Cabin cabin, Integer totalSeats, Integer availableSeats, FlightDtos.FlightResponse flight) implements Serializable {}
}
