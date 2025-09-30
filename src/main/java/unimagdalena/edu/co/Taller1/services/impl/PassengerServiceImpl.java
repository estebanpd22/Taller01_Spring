package unimagdalena.edu.co.Taller1.services.impl;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unimagdalena.edu.co.Taller1.domine.repositories.PassengerRepository;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.PassengerService;
import unimagdalena.edu.co.Taller1.services.mapper.PassengerMapper;
import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos.*;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;

    @Override @Transactional
    public PassengerResponse createPassenger(PassengerCreateRequest request) {
        var passenger = PassengerMapper.toEntity(request);
        return PassengerMapper.toResponse(passengerRepository.save(passenger));
    }

    @Override
    public PassengerResponse getPassenger(@Nonnull Long id) {
        return passengerRepository.findById(id).map(PassengerMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Passenger %d not found.".formatted(id)));
    }

    @Override
    public PassengerResponse getPassengerByEmail(@Nonnull String email) {
        return passengerRepository.findByEmailIgnoreCase(email).map(PassengerMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Passenger with email %s not found.".formatted(email)));
    }

    @Override
    public PassengerResponse getPassengerWithProfile(@Nonnull String email) {
        return passengerRepository.findByEmailIgnoreCase(email).map(PassengerMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Passenger with email %s not found.".formatted(email)));
    }

    @Override @Transactional
    public PassengerResponse updatePassenger(@Nonnull Long id, PassengerUpdateRequest request) {
        var passenger = passengerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Passenger %d not found.".formatted(id)));
        PassengerMapper.patch(passenger, request);
        return PassengerMapper.toResponse(passengerRepository.save(passenger));
    }

    @Override @Transactional
    public void deletePassenger(@Nonnull Long id) {
        passengerRepository.deleteById(id);
    }

    @Override
    public Page<PassengerResponse> listAllPassengers(Pageable pageable) {
        return passengerRepository.findAll(pageable).map(PassengerMapper::toResponse);
    }
}
