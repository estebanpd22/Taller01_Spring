package unimagdalena.edu.co.Taller1.services.mapper;

import unimagdalena.edu.co.Taller1.api.dto.AirlineDtos.*;
import unimagdalena.edu.co.Taller1.entities.Airline;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos.*;
import unimagdalena.edu.co.Taller1.entities.Flight;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class AirlineMapper {
    public static Airline toEntity(AirlineCreateRequest dto) {
        if (dto == null) return null;
        Airline entity = new Airline();
        entity.setCode(dto.code());
        entity.setName(dto.name());
        return entity;
    }

    public static Airline toUpdateEntity(AirlineUpdateRequest dto) {
        if (dto == null) return null;
        Airline entity = new Airline();
        entity.setCode(dto.code());
        entity.setName(dto.name());
        return entity;
    }

    public static AirlineResponse toResponse(Airline entity) {
        if (entity == null) return null;
        return new AirlineResponse(
                entity.getId(),
                entity.getCode(),
                entity.getName()
        );
    }
    public static AirlineDetailResponse toDetailResponse(
            Airline entity,
            boolean includeFlights,
            Function<Flight, FlightResponse> flightMapper
    ) {
        if (entity == null) return null;
        List<FlightResponse> flightsDto = List.of();
        if (includeFlights && entity.getFlights() != null && flightMapper != null) {
            flightsDto = entity.getFlights().stream()
                    .filter(Objects::nonNull)
                    .map(flightMapper)
                    .toList();
        }
        return new AirlineDetailResponse(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                flightsDto
        );
    }
}
