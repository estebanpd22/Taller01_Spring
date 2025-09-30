package unimagdalena.edu.co.Taller1.services.mapper;

import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Passenger;
import unimagdalena.edu.co.Taller1.domine.entities.PassengerProfile;

public class PassengerMapper {
    public static Passenger toEntity(PassengerCreateRequest passengerCreateRequest) {
        var profile = (passengerCreateRequest.profileDto() == null ) ? null :
                PassengerProfile.builder().phone(passengerCreateRequest.profileDto().phone())
                        .countryCode(passengerCreateRequest.profileDto().countryCode()).build();
        return Passenger.builder().fullName(passengerCreateRequest.fullName())
                .email(passengerCreateRequest.email()).profile(profile).build();


    }

    public static void UpdateEntity(PassengerUpdateRequest dto, Passenger passenger, PassengerProfile profile) {
        if (passenger == null || dto == null) return;
        if (dto.fullName() != null) {
            passenger.setFullName(dto.fullName());
        }
        if (dto.email() != null) {
            passenger.setEmail(dto.email());
        }
        if (profile != null) {
            passenger.setProfile(profile);

        }
    }

    public static PassengerResponse toResponse(Passenger passenger) {
        var profileDto= passenger.getProfile() == null ? null :
                new PassengerProfileDto(passenger.getProfile().getPhone(), passenger.getProfile().getCountryCode());
        return new PassengerResponse((long) passenger.getId(),passenger.getFullName(),passenger.getEmail(),profileDto);

    }

    public static void patch(Passenger entity, PassengerUpdateRequest request) {
        if (request.fullName() != null) entity.setFullName(request.fullName());
        if (request.email() != null) entity.setEmail(request.email());
        if (request.profileDto() != null) {
            var entityProfile =  entity.getProfile();
            if (entityProfile == null) {
                entityProfile = new PassengerProfile();
                entity.setProfile(entityProfile);
            }
            if (request.profileDto().phone() != null) entityProfile.setPhone(request.profileDto().phone());
            if (request.profileDto().countryCode() != null) entityProfile.setCountryCode(request.profileDto().countryCode());
        }
    }
}
