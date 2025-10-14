package unimagdalena.edu.co.Taller1.services.mapper;

import unimagdalena.edu.co.Taller1.api.dto.SeatInventoryDtos.*;
import unimagdalena.edu.co.Taller1.entities.Cabin;
import unimagdalena.edu.co.Taller1.entities.Flight;
import unimagdalena.edu.co.Taller1.entities.SeatInventory;

public class SeatInventoryMapper {

    public static SeatInventory toEntity(SeatInventoryCreateRequest request, Flight flight) {
        if (request == null) return null;

        if (flight == null) throw new NullPointerException("flight is null");

        return SeatInventory.builder()
                .cabin(request.cabin())
                .totalSeats(request.totalSeats())
                .availableSeats(request.availableSeats())
                .flight(flight)
                .build();
    }

    public static void UpdateEntity(SeatInventory seatInventory, SeatInventoryUpdateRequest request, Flight flight){
        if (seatInventory == null || request == null) return;

        if (request.cabin() != null) {
            seatInventory.setCabin(request.cabin());
        }
        if (request.totalSeats() != null) {
            seatInventory.setTotalSeats(request.totalSeats());
        }
        if (request.availableSeats() != null) {
            seatInventory.setAvailableSeats(request.availableSeats());
        }
        if (flight != null) {
            seatInventory.setFlight(flight);
        }
    }

    public static SeatInventoryResponse toResponse(SeatInventory seatInventory) {
        if (seatInventory == null) return null;
        return new SeatInventoryResponse(
                seatInventory.getId(),
                seatInventory.getCabin(),
                seatInventory.getTotalSeats(),
                seatInventory.getAvailableSeats(),
                FlightMapper.toResponse(seatInventory.getFlight())
        );

    }
}

