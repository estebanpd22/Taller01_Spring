package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.api.dto.AirlineDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Airline;

@Mapper(componentModel = "spring")
public interface AirlineMapperStruct {

    Airline toEntity(AirlineCreateRequest request);

     AirlineResponse toResponse(Airline entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(AirlineUpdateRequest request, @MappingTarget Airline entity);
}
