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
import unimagdalena.edu.co.Taller1.services.mapperStruct.AirlineMapperStruct;

import java.util.List;

@Service @Transactional @RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {
    private final AirlineRepository airlineRepository;
    private final AirlineMapperStruct airlineMapper;
    @Override
    public AirlineResponse create(AirlineCreateRequest request) {
        var airline = airlineMapper.toEntity(request);
        return airlineMapper.toResponse(airlineRepository.save(airline));
    }

    @Override @Transactional(readOnly = true)
    public AirlineResponse getById(@Nonnull Long id) {
        return airlineRepository.findById(id).map(airlineMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Airline %d not found.".formatted(id)));
    }

    @Override @Transactional(readOnly = true)
    public AirlineResponse getByCode(@Nonnull String code) {
        return airlineRepository.findByCodeIgnoreCase(code).map(airlineMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Airline with code %s not found.".formatted(code)));
    }

    @Override
    public AirlineResponse update(@Nonnull Long id, AirlineUpdateRequest request) {
        var airline = airlineRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Airline %d not found.".formatted(id)));

        airlineMapper.patch(airline, request);
        return airlineMapper.toResponse(airlineRepository.save(airline));
        //static void patch(Airline airline, AirlineUpdateRequest request) {
        //    }
    }

    @Override
    public void delete(@Nonnull Long id) {
        airlineRepository.deleteById(id);
    }

    @Override @Transactional(readOnly = true)
    public List<AirlineResponse> airlineList() {
        return airlineRepository.findAll().stream().map(airlineMapper::toResponse).toList();
    }

    @Override
    public Page<AirlineResponse> airlinePageList(Pageable pageable) {
        return airlineRepository.findAll(pageable).map(airlineMapper::toResponse);
    }
}