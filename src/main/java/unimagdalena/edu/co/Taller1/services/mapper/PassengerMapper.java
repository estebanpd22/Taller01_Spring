package unimagdalena.edu.co.Taller1.services.mapper;

import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos.*;
import unimagdalena.edu.co.Taller1.entities.Passenger;
import unimagdalena.edu.co.Taller1.entities.PassengerProfile;

public class PassengerMapper {
    public static Passenger toEntity(PassengerCreateRequest passengerCreateRequest) {
        var profile = (passengerCreateRequest.profileDto() == null ) ? null :
                PassengerProfile.builder().phone(passengerCreateRequest.profileDto().phone())
                        .countryCode(passengerCreateRequest.profileDto().countryCode()).build();
        return Passenger.builder().fullName(passengerCreateRequest.fullname())
                .email(passengerCreateRequest.email()).profile(profile).build();


    }

    public static void UpdateEntity(PassengerUpdateRequest dto, Passenger passenger, PassengerProfile profile) {
        if (passenger == null || dto == null) return;
        if (dto.fullname() != null) {
            passenger.setFullName(dto.fullname());
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
}
