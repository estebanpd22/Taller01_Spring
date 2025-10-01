package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos;
import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Passenger;
import unimagdalena.edu.co.Taller1.domine.entities.PassengerProfile;

@Mapper(componentModel = "spring")
public interface PassengerMapperStruct {

    @Mapping(source = "profileDto.phone", target = "profile.phone")
    @Mapping(source = "profileDto.countryCode", target = "profile.countryCode")
    Passenger toEntity(PassengerCreateRequest request);

    @Mapping(source = "profile.phone", target = "profileDto.phone")
    @Mapping(source = "profile.countryCode", target = "profileDto.countryCode")
    PassengerResponse toResponse(Passenger passenger);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "profileDto.phone", target = "profile.phone", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "profileDto.countryCode", target = "profile.countryCode", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePassengerFromDto(PassengerUpdateRequest request, @MappingTarget Passenger passenger);

    PassengerProfileDto toDto(PassengerProfile profile);

    PassengerProfile toEntity(PassengerProfileDto dto);

    void patch(Passenger entity, PassengerDtos.PassengerUpdateRequest request);
}
