package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.api.dto.AirlineDtos;
import unimagdalena.edu.co.Taller1.api.dto.AirportDtos;
import unimagdalena.edu.co.Taller1.api.dto.AirportDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Airline;
import unimagdalena.edu.co.Taller1.domine.entities.Airport;

@Mapper(componentModel = "spring")
public interface AirportMapperStruct {

    Airport toEntity(AirportCreateRequest request);

    AirportResponse toResponse(Airport entity);

    void patch(Airport airport, AirportDtos.AirportUpdateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAirportFromDto(AirportUpdateRequest request, @MappingTarget Airport entity);
}

