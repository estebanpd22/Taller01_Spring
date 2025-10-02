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
import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos.*;
import unimagdalena.edu.co.Taller1.services.mapperStruct.PassengerMapperStruct;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapperStruct passengerMapperStruct;

    @Override @Transactional
    public PassengerResponse create(PassengerCreateRequest request) {
        var passenger = passengerMapperStruct.toEntity(request);
        return passengerMapperStruct.toResponse(passengerRepository.save(passenger));
    }

    @Override
    public PassengerResponse getById(@Nonnull Long id) {
        return passengerRepository.findById(id).map(passengerMapperStruct::toResponse)
                .orElseThrow(() -> new NotFoundException("Passenger %d not found.".formatted(id)));
    }

    @Override
    public PassengerResponse getByEmail(@Nonnull String email) {
        return passengerRepository.findByEmailIgnoreCase(email).map(passengerMapperStruct::toResponse)
                .orElseThrow(() -> new NotFoundException("Passenger with email %s not found.".formatted(email)));
    }

    @Override
    public PassengerResponse getPassengerWithProfile(@Nonnull String email) {
        return passengerRepository.findByEmailIgnoreCase(email).map(passengerMapperStruct::toResponse)
                .orElseThrow(() -> new NotFoundException("Passenger with email %s not found.".formatted(email)));
    }

    @Override @Transactional
    public PassengerResponse update(@Nonnull Long id, PassengerUpdateRequest request) {
        var passenger = passengerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Passenger %d not found.".formatted(id)));
        passengerMapperStruct.patch(passenger, request);
        return passengerMapperStruct.toResponse(passengerRepository.save(passenger));
    }

    @Override @Transactional
    public void delete(@Nonnull Long id) {
        passengerRepository.deleteById(id);
    }

    @Override
    public Page<PassengerResponse> listAllPassengers(Pageable pageable) {
        return passengerRepository.findAll(pageable).map(passengerMapperStruct::toResponse);
    }
}
