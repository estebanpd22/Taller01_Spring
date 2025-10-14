package unimagdalena.edu.co.Taller1.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unimagdalena.edu.co.Taller1.api.dto.AirportDtos.*;
import unimagdalena.edu.co.Taller1.entities.Airport;
import unimagdalena.edu.co.Taller1.repositories.AirportRepository;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.AirportService;
import unimagdalena.edu.co.Taller1.services.mapper.AirportMapper;

@Transactional
@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    @Override
    public AirportResponse create(AirportCreateRequest request) {
        String code = request.code().trim().toUpperCase();

        airportRepository.findByCodeIgnoreCase(code).ifPresent(a -> {
            throw new IllegalStateException("Airport code already exists: " + code);
        });

        Airport toSave = AirportMapper.toEntity(request);
        toSave.setCode(code);

        Airport saved = airportRepository.save(toSave);
        return AirportMapper.toResponse(saved);
    }

    @Override
    public AirportResponse get(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Airport not found: id=" + id));
        return AirportMapper.toResponse(airport);
    }

    @Override
    public AirportResponse getByCode(String rawCode) {
        String code = rawCode.trim().toUpperCase();
        Airport airport = airportRepository.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new NotFoundException("Airport not found: code=" + code));
        return AirportMapper.toResponse(airport);
    }

    @Override
    public AirportResponse update(Long id, AirportUpdateRequest request) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Airport not found: id=" + id));

        String newCode = request.code() == null ? null : request.code().trim().toUpperCase();
        if (newCode == null || newCode.isBlank()) {
            throw new IllegalArgumentException("Airport code cannot be blank");
        }

        if (!airport.getCode().equalsIgnoreCase(newCode)) {
            airportRepository.findByCodeIgnoreCase(newCode).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new IllegalStateException("Airport code already exists: " + newCode);
                }
            });
        }

        airport.setCode(newCode);
        airport.setName(request.name());
        airport.setCity(request.city());

        Airport updated = airportRepository.save(airport);

        return AirportMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if (!airportRepository.existsById(id)) {
            throw new NotFoundException("Airport not found: id=" + id);
        }
        airportRepository.deleteById(id);
    }
}