package unimagdalena.edu.co.Taller1.services.mapper;

import unimagdalena.edu.co.Taller1.api.dto.FlightDtos.*;
import unimagdalena.edu.co.Taller1.entities.Airline;
import unimagdalena.edu.co.Taller1.entities.Airport;
import unimagdalena.edu.co.Taller1.entities.Flight;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class FlightMapper {

    public static Flight toEntity(FlightCreateRequest dto) {
        if (dto == null) return null;
        Flight entity = new Flight();
        entity.setNumber(dto.number());
        entity.setDepartureTime(dto.departureTime());
        entity.setArrivalTime(dto.arrivalTime());
        // Resto de atributos en services
        return entity;
    }

    public static void toUpdateEntity(FlightUpdateRequest dto, Flight entity) {
        if (dto == null || entity == null) return;
        entity.setNumber(dto.number());
        entity.setDepartureTime(dto.departureTime());
        entity.setArrivalTime(dto.arrivalTime());
        // Resto de atributos en services
    }

    public static FlightResponse toResponse(Flight entity) {
        if (entity == null) return null;

        AirlineRef airlineRef = null;
        if (entity.getAirline() != null) {
            Airline a = entity.getAirline();
            airlineRef = new AirlineRef(a.getId(), a.getCode(), a.getName());
        }

        AirportRef originRef = null;
        if (entity.getOrigin() != null) {
            Airport o = entity.getOrigin();
            originRef = new AirportRef(o.getId(), o.getCode(), o.getCity());
        }

        AirportRef destinationRef = null;
        if (entity.getDestination() != null) {
            Airport d = entity.getDestination();
            destinationRef = new AirportRef(d.getId(), d.getCode(), d.getCity());
        }

        Set<TagRef> tagRefs = Set.of();
        if (entity.getTags() != null) {
            tagRefs = entity.getTags().stream()
                    .filter(Objects::nonNull)
                    .map(t -> new TagRef(t.getId(), t.getName()))
                    .collect(Collectors.toSet());
        }

        return new FlightResponse(
                entity.getId(),
                entity.getNumber(),
                entity.getDepartureTime(),
                entity.getArrivalTime(),
                airlineRef,
                originRef,
                destinationRef,
                tagRefs
        );
    }
}

