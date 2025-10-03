package unimagdalena.edu.co.Taller1.services.mapperStruct;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import unimagdalena.edu.co.Taller1.api.dto.SeatInventoryDtos;
import unimagdalena.edu.co.Taller1.domine.entities.SeatInventory;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-03T14:00:05-0500",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class SeatInventoryMapperStructImpl implements SeatInventoryMapperStruct {

    @Override
    public SeatInventory toEntity(SeatInventoryDtos.SeatInventoryCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        SeatInventory.SeatInventoryBuilder seatInventory = SeatInventory.builder();

        seatInventory.cabin( request.cabin() );
        seatInventory.totalSeats( request.totalSeats() );
        seatInventory.availableSeats( request.availableSeats() );

        return seatInventory.build();
    }

    @Override
    public SeatInventoryDtos.SeatInventoryResponse toResponse(SeatInventory entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String cabin = null;
        Integer totalSeats = null;
        Integer availableSeats = null;

        id = entity.getId();
        cabin = map( entity.getCabin() );
        totalSeats = entity.getTotalSeats();
        availableSeats = entity.getAvailableSeats();

        Long flight_id = null;

        SeatInventoryDtos.SeatInventoryResponse seatInventoryResponse = new SeatInventoryDtos.SeatInventoryResponse( id, cabin, totalSeats, availableSeats, flight_id );

        return seatInventoryResponse;
    }

    @Override
    public void patch(SeatInventory entity, SeatInventoryDtos.SeatInventoryUpdateRequest request) {
        if ( entity == null ) {
            return;
        }
    }
}
