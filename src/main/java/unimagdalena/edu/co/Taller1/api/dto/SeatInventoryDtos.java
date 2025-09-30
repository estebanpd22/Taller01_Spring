package unimagdalena.edu.co.Taller1.api.dto;

import unimagdalena.edu.co.Taller1.domine.entities.Cabin;

import java.io.Serializable;

public class SeatInventoryDtos {
    public record SeatInventoryCreateRequest(Cabin cabin, Integer totalSeats, Integer availableSeats,Long flightId) implements Serializable {}
    public record SeatInventoryUpdateRequest(Long id, Cabin cabin, Integer totalSeats, Integer availableSeats, Long flightId) implements Serializable {}
    public record SeatInventoryResponse(Long id, String cabin, Integer totalSeats, Integer availableSeats, Long flight_id) implements Serializable {}
}

