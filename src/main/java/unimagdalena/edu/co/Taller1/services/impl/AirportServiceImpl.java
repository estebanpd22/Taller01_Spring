package unimagdalena.edu.co.Taller1.services.impl;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unimagdalena.edu.co.Taller1.api.dto.AirportDtos.*;
import unimagdalena.edu.co.Taller1.domine.repositories.AirportRepository;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.AirportService;
import unimagdalena.edu.co.Taller1.services.mapper.AirportMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {
    private final AirportRepository airportRepository;
    @Override @Transactional
    public AirportResponse createAirport(AirportCreateRequest request) {
        var airport = AirportMapper.toEntity(request);
        return AirportMapper.toResponse(airportRepository.save(airport));
    }

    @Override
    public AirportResponse getAirport(@Nonnull Long id) {
        return airportRepository.findById(id).map(AirportMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Airport %d not found.".formatted(id)));
    }

    @Override
    public AirportResponse getAirportByCode(@Nonnull String code) {
        return airportRepository.findByCodeIgnoreCase(code).map(AirportMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Airport with code %s not found.".formatted(code)));
    }

    @Override
    public List<AirportResponse> getCityAirports(@Nonnull String city) {
        return airportRepository.findByCity(city).stream().map(AirportMapper::toResponse).toList();
    }

    @Override @Transactional
    public AirportResponse updateAirport(@Nonnull Long id, AirportUpdateRequest request) {
        var airport = airportRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Airport %d not found.".formatted(id)));
        AirportMapper.patch(airport, request);
        return AirportMapper.toResponse(airportRepository.save(airport));
    }

    @Override @Transactional
    public void deleteAirport(@Nonnull Long id) {
        airportRepository.deleteById(id);
    }

    @Override
    public Page<AirportResponse> listAllAirports(Pageable pageable) {
        return airportRepository.findAll(pageable).map(AirportMapper::toResponse);
    }
}