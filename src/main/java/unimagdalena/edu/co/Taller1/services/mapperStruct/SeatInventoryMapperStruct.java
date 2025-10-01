package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.api.dto.SeatInventoryDtos;
import unimagdalena.edu.co.Taller1.api.dto.SeatInventoryDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.SeatInventory;
import unimagdalena.edu.co.Taller1.domine.entities.Cabin;

@Mapper(componentModel = "spring")
public interface SeatInventoryMapperStruct {

    SeatInventory toEntity(SeatInventoryCreateRequest request);

    @Mapping(source = "cabin", target = "cabin")
    @Mapping(source = "flight.id", target = "flight_id")
    SeatInventoryResponse toResponse(SeatInventory entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSeatInventoryFromDto(SeatInventoryUpdateRequest request, @MappingTarget SeatInventory entity);

    void patch(SeatInventory entity, SeatInventoryDtos.SeatInventoryUpdateRequest request);

    default String map(Cabin cabin) {
        return cabin != null ? cabin.name() : null;
    }

    default Cabin map(String cabin) {
        return cabin != null ? Cabin.valueOf(cabin.toUpperCase()) : null;
    }
}
