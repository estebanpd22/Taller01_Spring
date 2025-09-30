package unimagdalena.edu.co.Taller1.services.mapper;

import unimagdalena.edu.co.Taller1.api.dto.SeatInventoryDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Cabin;
import unimagdalena.edu.co.Taller1.domine.entities.SeatInventory;

public class SeatInventoryMapper {
    public static SeatInventory toEntity(SeatInventoryCreateRequest request ) {
        return SeatInventory.builder().cabin(Cabin.valueOf(String.valueOf(request.cabin()))).availableSeats(request.availableSeats())
                .totalSeats(request.availableSeats()).build();
    }
    public static SeatInventoryResponse toResponse(SeatInventory seatInventory) {
        return new SeatInventoryResponse(
                seatInventory.getId(), seatInventory.getCabin().name(),
                seatInventory.getTotalSeats(), seatInventory.getAvailableSeats(), seatInventory.getFlight().getId()
        );
    }

    public static void patch(SeatInventory entity, SeatInventoryUpdateRequest update) {
        if (update.cabin() != null) entity.setCabin(Cabin.valueOf(String.valueOf(update.cabin())));
        if (update.totalSeats() != null) entity.setTotalSeats(update.totalSeats());
        if (update.availableSeats() != null) entity.setAvailableSeats(update.availableSeats());
    }
}

