/*package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.api.dto.AirportDtos.*;
import unimagdalena.edu.co.Taller1.entities.Airport;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)

public interface AirportMapperStruct {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "originFlights", ignore = true)
    @Mapping(target = "destinationFlights", ignore = true)
    Airport toEntity(AirportCreateRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "originFlights", ignore = true)
    @Mapping(target = "destinationFlights", ignore = true)
    void updateEntity(@MappingTarget Airport entity, AirportUpdateRequest dto);

    AirportResponse toResponse(Airport entity);
}
*/
