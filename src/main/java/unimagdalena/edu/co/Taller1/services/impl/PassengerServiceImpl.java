package unimagdalena.edu.co.Taller1.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unimagdalena.edu.co.Taller1.entities.Passenger;
import unimagdalena.edu.co.Taller1.entities.PassengerProfile;
import unimagdalena.edu.co.Taller1.repositories.PassengerProfileRepository;
import unimagdalena.edu.co.Taller1.repositories.PassengerRepository;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.PassengerService;
import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos.*;
import unimagdalena.edu.co.Taller1.services.mapper.PassengerMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerProfileRepository passengerProfileRepository;

    @Override
    public PassengerResponse createPassenger(PassengerCreateRequest request) {
        if (request == null)throw new NotFoundException("Passenger not found");
        if (request.email() == null)throw new NotFoundException("Passenger email not found");
        passengerRepository.findByEmailIgnoreCase(request.email()).ifPresent(passenger -> {throw new IllegalArgumentException("Passenger email already exists");});
        PassengerProfile passengerProfile = null;
        if (request.profileDto() != null){
            passengerProfile = PassengerProfile.builder().phone(request.profileDto().phone())
                    .countryCode(request.profileDto().countryCode()).build();
        }
        Passenger passenger = PassengerMapper.toEntity(request);
        return PassengerMapper.toResponse( passengerRepository.save(passenger));

    }

    @Override
    public PassengerResponse updatePassenger(Long id,PassengerUpdateRequest request) {
        if (request == null) throw new IllegalArgumentException("PassengerUpdateRequest cannot be null");

        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Passenger not found with id: " + id));

        // Validar email si se cambia
        if (request.email() != null && !request.email().equalsIgnoreCase(passenger.getEmail())) {
            passengerRepository.findByEmailIgnoreCase(request.email())
                    .ifPresent(p -> { throw new IllegalArgumentException("Email already in use: " + request.email()); });
            passenger.setEmail(request.email());
        }

        if (request.fullname() != null) {
            passenger.setFullName(request.fullname());
        }

        if (request.profileDto() != null) {
            PassengerProfile profile = passenger.getProfile();
            if (profile == null) {
                profile = new PassengerProfile();
                passenger.setProfile(profile);
            }
            profile.setPhone(request.profileDto().phone());
            profile.setCountryCode(request.profileDto().countryCode());
        }

        Passenger updated = passengerRepository.save(passenger);
        return PassengerMapper.toResponse(updated);
    }

    @Override
    public PassengerResponse get(Long id) {
        Passenger passenger = passengerRepository.findById(id).orElseThrow(() -> new NotFoundException("Passenger not found with id: " + id));
        return PassengerMapper.toResponse(passenger);
    }

    @Override
    public PassengerResponse getByEmail(String email) {
        Passenger passenger = passengerRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new NotFoundException("Passenger not found with email: " + email));
        return PassengerMapper.toResponse(passenger);
    }

    @Override
    public PassengerResponse getByEmailWithProfile(String email) {
        Passenger passenger = passengerRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new NotFoundException("Passenger not found with email: " + email));
        return PassengerMapper.toResponse(passenger);
    }

    @Override
    public void delete(Long id) {
        Passenger passenger = passengerRepository.findById(id).orElseThrow(() -> new NotFoundException("Passenger not found with id: " + id));
        passengerRepository.delete(passenger);
    }
}
