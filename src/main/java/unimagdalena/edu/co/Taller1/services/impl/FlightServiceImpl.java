package unimagdalena.edu.co.Taller1.services.impl;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos.*;
import unimagdalena.edu.co.Taller1.entities.Airline;
import unimagdalena.edu.co.Taller1.entities.Airport;
import unimagdalena.edu.co.Taller1.entities.Flight;
import unimagdalena.edu.co.Taller1.entities.Tag;
import unimagdalena.edu.co.Taller1.repositories.AirlineRepository;
import unimagdalena.edu.co.Taller1.repositories.AirportRepository;
import unimagdalena.edu.co.Taller1.repositories.FlightRepository;
import unimagdalena.edu.co.Taller1.repositories.TagRepository;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.FlightService;
import unimagdalena.edu.co.Taller1.services.mapper.FlightMapper;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Transactional
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public FlightResponse create(FlightCreateRequest request) {
        Airline airline = airlineRepository.findById(request.airlineId())
                .orElseThrow(() -> new NotFoundException("Airline not found with id: " + request.airlineId()));

        Airport origin = airportRepository.findById(request.originId())
                .orElseThrow(() -> new NotFoundException("Origin airport not found with id: " + request.originId()));

        Airport destination = airportRepository.findById(request.destinationId())
                .orElseThrow(() -> new NotFoundException("Destination airport not found with id: " + request.destinationId()));

        Set<Tag> tags = new HashSet<>();
        if (request.tagIds() != null && !request.tagIds().isEmpty()) {
            tags = new HashSet<>(tagRepository.findAllById(request.tagIds()));
        }

        Flight flight = Flight.builder()
                .number(request.number())
                .departureTime(request.departureTime())
                .arrivalTime(request.arrivalTime())
                .airline(airline)
                .origin(origin)
                .destination(destination)
                .tags(tags)
                .build();

        Flight saved = flightRepository.save(flight);
        return FlightMapper.toResponse(saved);
    }

    @Override
    public FlightResponse getById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Flight not found with id: " + id));
        return FlightMapper.toResponse(flight);
    }

    @Override
    public List<FlightResponse> getAll() {
        return flightRepository.findAll().stream()
                .map(FlightMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FlightResponse update(Long id, FlightUpdateRequest request) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Flight not found with id: " + id));

        if (request.airlineId() != null && !request.airlineId().equals(flight.getAirline().getId())) {
            Airline airline = airlineRepository.findById(request.airlineId())
                    .orElseThrow(() -> new NotFoundException("Airline not found with id: " + request.airlineId()));
            flight.setAirline(airline);
        }

        if (request.originId() != null && !request.originId().equals(flight.getOrigin().getId())) {
            Airport origin = airportRepository.findById(request.originId())
                    .orElseThrow(() -> new NotFoundException("Origin airport not found with id: " + request.originId()));
            flight.setOrigin(origin);
        }

        if (request.destinationId() != null && !request.destinationId().equals(flight.getDestination().getId())) {
            Airport destination = airportRepository.findById(request.destinationId())
                    .orElseThrow(() -> new NotFoundException("Destination airport not found with id: " + request.destinationId()));
            flight.setDestination(destination);
        }

        if (request.number() != null) {
            flight.setNumber(request.number());
        }
        if (request.departureTime() != null) {
            flight.setDepartureTime(request.departureTime());
        }
        if (request.arrivalTime() != null) {
            flight.setArrivalTime(request.arrivalTime());
        }

        if (request.tagIds() != null) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.tagIds()));
            flight.setTags(tags);
        }

        Flight updated = flightRepository.save(flight);
        return FlightMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new NotFoundException("Flight not found with id: " + id);
        }
        flightRepository.deleteById(id);
    }

    @Override
    public Page<FlightResponse> findByAirlineName(String airlineName, Pageable pageable) {
        return flightRepository.findByAirline_Name(airlineName, pageable)
                .map(FlightMapper::toResponse);
    }

    @Override
    public Page<FlightResponse> searchFlights(
            String originCode,
            String destinationCode,
            OffsetDateTime from,
            OffsetDateTime to,
            Pageable pageable) {
        return flightRepository.findByOrigin_CodeAndDestination_CodeAndDepartureTimeBetween(
                        originCode, destinationCode, from, to, pageable)
                .map(FlightMapper::toResponse);
    }
}
