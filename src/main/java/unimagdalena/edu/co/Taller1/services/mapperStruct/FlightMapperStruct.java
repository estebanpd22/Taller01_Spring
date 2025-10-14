/*
package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos.*;
import unimagdalena.edu.co.Taller1.entities.Flight;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface FlightMapperStruct {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "airline", ignore = true)
    @Mapping(target = "origin", ignore = true)
    @Mapping(target = "destination", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "bookingItems", ignore = true)
    @Mapping(target = "seatInventories", ignore = true)
    Flight toEntity(FlightCreateRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "airline", ignore = true)
    @Mapping(target = "origin", ignore = true)
    @Mapping(target = "destination", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "bookingItems", ignore = true)
    @Mapping(target = "seatInventories", ignore = true)
    void updateEntity(@MappingTarget Flight entity, FlightUpdateRequest dto);

    @Mapping(target = "airline", ignore = true)
    @Mapping(target = "origin", ignore = true)
    @Mapping(target = "destination", ignore = true)
    @Mapping(target = "tags", ignore = true)
    FlightResponse toResponse(Flight entity);

}
*/