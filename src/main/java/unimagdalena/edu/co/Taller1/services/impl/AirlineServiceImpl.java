package unimagdalena.edu.co.Taller1.services.impl;

import unimagdalena.edu.co.Taller1.api.dto.AirlineDtos.*;
import unimagdalena.edu.co.Taller1.domine.repositories.AirlineRepository;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.AirlineService;
import unimagdalena.edu.co.Taller1.services.mapper.AirlineMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @Transactional @RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {
    private final AirlineRepository airlineRepository;

    @Override
    public AirlineResponse createAirline(AirlineCreateRequest request) {
        var airline = AirlineMapper.toEntity(request);
        return AirlineMapper.toResponse(airlineRepository.save(airline));
    }

    @Override @Transactional(readOnly = true)
    public AirlineResponse getAirline(@Nonnull Long id) {
        return airlineRepository.findById(id).map(AirlineMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Airline %d not found.".formatted(id)));
    }

    @Override @Transactional(readOnly = true)
    public AirlineResponse getAirlineByCode(@Nonnull String code) {
        return airlineRepository.findByCodeIgnoreCase(code).map(AirlineMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Airline with code %s not found.".formatted(code)));
    }

    @Override
    public AirlineResponse updateAirline(@Nonnull Long id, AirlineUpdateRequest request) {
        var airline = airlineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Airline %d not found.".formatted(id)));
        AirlineMapper.patch(airline, request);
        return AirlineMapper.toResponse(airlineRepository.save(airline));
    }

    @Override
    public void deleteAirline(@Nonnull Long id) {
        airlineRepository.deleteById(id);
    }

    @Override @Transactional(readOnly = true)
    public List<AirlineResponse> listAllAirlines() {
        return airlineRepository.findAll().stream().map(AirlineMapper::toResponse).toList();
    }

    @Override
    public Page<AirlineResponse> listAllAirlinesPage(Pageable pageable) {
        return airlineRepository.findAll(pageable).map(AirlineMapper::toResponse);
    }
}