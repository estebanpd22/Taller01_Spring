package unimagdalena.edu.co.Taller1.services.mapperStruct;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos;
import unimagdalena.edu.co.Taller1.domine.entities.Passenger;
import unimagdalena.edu.co.Taller1.domine.entities.PassengerProfile;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-03T11:21:29-0500",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class PassengerMapperStructImpl implements PassengerMapperStruct {

    @Override
    public Passenger toEntity(PassengerDtos.PassengerCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Passenger passenger = new Passenger();

        return passenger;
    }

    @Override
    public PassengerDtos.PassengerResponse toResponse(Passenger passenger) {
        if ( passenger == null ) {
            return null;
        }

        Long id = null;
        String fullName = null;
        String email = null;
        PassengerDtos.PassengerProfileDto profileDto = null;

        PassengerDtos.PassengerResponse passengerResponse = new PassengerDtos.PassengerResponse( id, fullName, email, profileDto );

        return passengerResponse;
    }

    @Override
    public void patch(PassengerDtos.PassengerUpdateRequest request, Passenger passenger) {
        if ( request == null ) {
            return;
        }
    }

    @Override
    public PassengerDtos.PassengerProfileDto toDto(PassengerProfile profile) {
        if ( profile == null ) {
            return null;
        }

        String phone = null;
        String countryCode = null;

        PassengerDtos.PassengerProfileDto passengerProfileDto = new PassengerDtos.PassengerProfileDto( phone, countryCode );

        return passengerProfileDto;
    }

    @Override
    public PassengerProfile toEntity(PassengerDtos.PassengerProfileDto dto) {
        if ( dto == null ) {
            return null;
        }

        PassengerProfile passengerProfile = new PassengerProfile();

        return passengerProfile;
    }
}
