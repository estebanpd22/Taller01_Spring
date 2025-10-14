/*
package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos.*;
import unimagdalena.edu.co.Taller1.entities.Passenger;
import unimagdalena.edu.co.Taller1.entities.PassengerProfile;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PassengerMapperStruct {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "fullName", source = "fullname")
    @Mapping(target = "profile", source = "profileDto")
    Passenger toEntity(PassengerCreateRequest request);

    PassengerProfile toProfile(PassengerProfileDto profileDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "fullName", source = "dto.fullname")
    @Mapping(target = "email", source = "dto.email")
    @Mapping(target = "profile", source = "profile")
    void updateEntity(
            @MappingTarget Passenger passenger,
            PassengerUpdateRequest dto,
            PassengerProfile profile
    );

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullname", source = "fullName")
    @Mapping(target = "profileDto", source = "profile")
    PassengerResponse toResponse(Passenger passenger);

    PassengerProfileDto toProfileDto(PassengerProfile profile);
}
*/