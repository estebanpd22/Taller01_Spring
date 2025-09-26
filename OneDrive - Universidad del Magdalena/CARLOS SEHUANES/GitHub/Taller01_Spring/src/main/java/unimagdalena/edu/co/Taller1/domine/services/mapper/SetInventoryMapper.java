package unimagdalena.edu.co.Taller1.domine.services.mapper;

import unimagdalena.edu.co.Taller1.domine.entities.SeatInventory;

public class SetInventoryMapper {
    private final FlightMapper flightMapper;

    public SeatInventoryMapper(FlightMapper flightMapper) {
        this.flightMapper = flightMapper;
    }

    public static SeatInventory toEntity(SeatInventoryDtos.SeatInventoryCreateRequest request, Flight flight) {
        if (request == null) return null;

        if (flight == null) throw new NullPointerException("flight is null");

        return SeatInventory.builder()
                .cabin(request.cabin())
                .totalSeats(request.totalSeats())
                .availableSeats(request.availableSeats())
                .flight(flight)
                .build();
    }

    public static void UpdateEntity(SeatInventory seatInventory, SeatInventoryDtos.SeatInventoryUpdateRequest request, Flight flight){
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

    public SeatInventoryDtos.SeatInventoryResponse toResponse(SeatInventory seatInventory) {
        if (seatInventory == null) return null;
        return new SeatInventoryDtos.SeatInventoryResponse(
                seatInventory.getId(),
                seatInventory.getCabin(),
                seatInventory.getTotalSeats(),
                seatInventory.getAvailableSeats(),
                flightMapper.toResponse(seatInventory.getFlight())
        );

    }


}
