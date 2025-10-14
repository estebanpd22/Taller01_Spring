/*package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.api.dto.AirlineDtos.*;
import unimagdalena.edu.co.Taller1.entities.Airline;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AirlineMapperStruct {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flights", ignore = true)
    Airline toEntity(AirlineCreateRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flights", ignore = true)
    void updateEntity(@MappingTarget Airline entity, AirlineUpdateRequest dto);

    AirlineResponse toResponse(Airline entity);

    @Mapping(target = "flights", ignore = true)
    AirlineDetailResponse toDetailResponse(Airline entity);
}
*/