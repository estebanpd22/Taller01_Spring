package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos;
import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Passenger;
import unimagdalena.edu.co.Taller1.domine.entities.PassengerProfile;

@Mapper(componentModel = "spring")
public interface PassengerMapperStruct {

    Passenger toEntity(PassengerCreateRequest request);

    PassengerResponse toResponse(Passenger passenger);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(PassengerUpdateRequest request, @MappingTarget Passenger passenger);

    PassengerProfileDto toDto(PassengerProfile profile);

    PassengerProfile toEntity(PassengerProfileDto dto);
}
