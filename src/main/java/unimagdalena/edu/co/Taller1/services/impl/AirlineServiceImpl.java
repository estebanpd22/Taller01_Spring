package unimagdalena.edu.co.Taller1.services.impl;

import unimagdalena.edu.co.Taller1.api.dto.AirlineDtos.*;
import unimagdalena.edu.co.Taller1.entities.Airline;
import unimagdalena.edu.co.Taller1.repositories.AirlineRepository;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unimagdalena.edu.co.Taller1.services.mapper.AirlineMapper;


@Service @Transactional @RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;

    private final AirlineMapper airlineMapper;

    @Override
    public AirlineResponse create(AirlineCreateRequest request) {
        String code = request.code().trim().toUpperCase();

        airlineRepository.findByCodeIgnoreCase(code).ifPresent(a -> {
            throw new IllegalStateException("Airline code already exists: " + code);
        });

        Airline toSave = AirlineMapper.toEntity(request);
        toSave.setCode(code);

        Airline saved = airlineRepository.save(toSave);
        return AirlineMapper.toResponse(saved);
    }

    @Override
    public AirlineResponse get(Long id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Airline not found: id=" + id));
        return AirlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse getByCode(String rawCode) {
        String code = rawCode.trim().toUpperCase();
        Airline airline = airlineRepository.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new NotFoundException("Airline not found: code=" + code));
        return AirlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse update(Long id, AirlineUpdateRequest request) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Airline not found: id=" + id));

        String newCode = request.code() == null ? null : request.code().trim().toUpperCase();
        if (newCode == null || newCode.isBlank()) {
            throw new IllegalArgumentException("Airline code cannot be blank");
        }

        if (!airline.getCode().equalsIgnoreCase(newCode)) {
            airlineRepository.findByCodeIgnoreCase(newCode).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    throw new IllegalStateException("Airline code already exists: " + newCode);
                }
            });
        }

        airline.setCode(newCode);
        airline.setName(request.name());

        Airline updated = airlineRepository.save(airline);

        return AirlineMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if (!airlineRepository.existsById(id)) {
            throw new NotFoundException("Airline not found: id=" + id);
        }
        airlineRepository.deleteById(id);
    }

    // ==== Helpers ====
    private boolean equalsIgnoreCaseSafe(String a, String b) {
        if (a == null) return b == null;
        return a.equalsIgnoreCase(b);
    }
}