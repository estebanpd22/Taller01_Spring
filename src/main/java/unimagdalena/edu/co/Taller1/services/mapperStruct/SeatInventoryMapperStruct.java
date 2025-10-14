/*package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.api.dto.SeatInventoryDtos.*;
import unimagdalena.edu.co.Taller1.entities.Flight;
import unimagdalena.edu.co.Taller1.entities.SeatInventory;
import unimagdalena.edu.co.Taller1.entities.Cabin;

@Mapper(
        componentModel = "spring",
        uses = {FlightMapperStruct.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SeatInventoryMapperStruct {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flight", source = "flight")
    SeatInventory toEntity(SeatInventoryCreateRequest request, Flight flight);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flight", source = "flight")
    void updateEntity(
            @MappingTarget SeatInventory seatInventory,
            SeatInventoryUpdateRequest request,
            Flight flight
    );

    SeatInventoryResponse toResponse(SeatInventory seatInventory);
}
*/