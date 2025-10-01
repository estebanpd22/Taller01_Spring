package unimagdalena.edu.co.Taller1.services.impl;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Airport;
import unimagdalena.edu.co.Taller1.domine.entities.Flight;
import unimagdalena.edu.co.Taller1.domine.repositories.AirlineRepository;
import unimagdalena.edu.co.Taller1.domine.repositories.AirportRepository;
import unimagdalena.edu.co.Taller1.domine.repositories.FlightRepository;
import unimagdalena.edu.co.Taller1.domine.repositories.TagRepository;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.FlightService;
import unimagdalena.edu.co.Taller1.services.mapper.FlightMapper;
import unimagdalena.edu.co.Taller1.services.mapperStruct.FlightMapperStruct;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final TagRepository tagRepository;
    private final FlightMapperStruct flightMapperStruct;

    @Override
    public FlightResponse createFlight(FlightCreateRequest request, @Nonnull Long airline_id, @Nonnull Long origin_airport_id, @Nonnull Long destination_airport_id) {
        var airline = airlineRepository.findById(airline_id).orElseThrow(
                () -> new NotFoundException("Airline %d not found.".formatted(airline_id))
        );
        var origin_airport = airportRepository.findById(origin_airport_id).orElseThrow(
                () -> new NotFoundException("Airport %d not found.".formatted(origin_airport_id))
        );
        var destination_airport = airportRepository.findById(destination_airport_id).orElseThrow(
                () -> new NotFoundException("Airport %d not found.".formatted(destination_airport_id))
        );

        Flight f = flightMapperStruct.toEntity(request);
        f.setAirline(airline);
        f.setOrigin(origin_airport);
        f.setDestination(destination_airport);

        return flightMapperStruct.toResponse(f);
    }

    @Override @Transactional(readOnly = true)
    public FlightResponse getFlight(@Nonnull Long id) {
        return flightRepository.findById(id).map(flightMapperStruct::toResponse).orElseThrow(
                () -> new NotFoundException("Flight %d not found.".formatted(id))
        );
    }

    @Override //A flight just can update his destination airport, I looked into it
    public FlightResponse updateFlight(FlightUpdateRequest request, @Nonnull Long id) {
        var flight = flightRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Flight %d not found.".formatted(id))
        );
        flightMapperStruct.patch(flight, request);
        if (request.destinationId() != null) {
            var destination = airportRepository.findById(request.destinationId())
                    .orElseThrow(() -> new NotFoundException("Airport %d not found."
                            .formatted(request.destinationId())));
            flight.setDestination(destination);
        }
        return flightMapperStruct.toResponse(flightRepository.save(flight));
    }

    @Override
    public void deleteFlight(@Nonnull Long id) {
        flightRepository.deleteById(id);
    }

    @Override @Transactional(readOnly = true)
    public Page<FlightResponse> listScheduledFlights(Long origin_airport_id, Long destination_airport_id, @Nonnull OffsetDateTime from, @Nonnull OffsetDateTime to, Pageable pageable) {
        if (from.isAfter(to)) throw new IllegalArgumentException("\"From\" date is after \"to\" date");

        var origin = (origin_airport_id != null)?  airportRepository.findById(origin_airport_id): Optional.<Airport>empty();
        var destination = (destination_airport_id != null)? airportRepository.findById(destination_airport_id) : Optional.<Airport>empty();

        var flights = (origin.isPresent() && destination.isPresent())?
                flightRepository.findByOrigin_CodeAndDestination_CodeAndDepartureTimeBetween(origin.get().getCode(), destination.get().getCode(), from, to, pageable):
                new PageImpl<>(
                        flightRepository.filterByOriginAndDestinationOptionalAndDepartureTimeBetween(
                                origin.map(Airport::getCode).orElse(null), destination.map(Airport::getCode).orElse(null), from, to
                        )
                );

        return flights.map(flightMapperStruct::toResponse);
    }

    @Override
    public FlightResponse addTagToFlight(@Nonnull Long flight_id, @Nonnull Long tag_id) {
        var flight = flightRepository.findById(flight_id).orElseThrow(() -> new NotFoundException("Flight %d not found".formatted(flight_id)));
        var tag = tagRepository.findById(tag_id).orElseThrow(() -> new NotFoundException("Tag %d not found".formatted(tag_id)));
        flightMapperStruct.addTag(flight, tag);

        return flightMapperStruct.toResponse(flight);
    }

    @Override
    public FlightResponse removeTagFromFlight(@Nonnull Long flight_id, @Nonnull Long tag_id) {
        var flight = flightRepository.findById(flight_id).orElseThrow(() -> new NotFoundException("Flight %d not found".formatted(flight_id)));
        var tag = tagRepository.findById(tag_id).orElseThrow(() -> new NotFoundException("Tag %d not found".formatted(tag_id)));

        flight.getTags().remove(tag);
        tag.getFlights().remove(flight);

        return flightMapperStruct.toResponse(flight);
    }

    @Override @Transactional(readOnly = true)
    public Page<FlightResponse> listFlightsByAirline(@Nonnull Long airline_id, Pageable pageable) {
        var airline = airlineRepository.findById(airline_id).orElseThrow(() -> new NotFoundException("Airline %d not found".formatted(airline_id)));
        return flightRepository.findByAirline_Name(airline.getName(), pageable).map(flightMapperStruct::toResponse);
    }
}
