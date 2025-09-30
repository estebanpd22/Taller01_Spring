package unimagdalena.edu.co.Taller1.services.mapper;

import unimagdalena.edu.co.Taller1.api.dto.FlightDtos.*;
import unimagdalena.edu.co.Taller1.api.dto.TagDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Tag;
import unimagdalena.edu.co.Taller1.domine.entities.Flight;
import java.util.Set;
import java.util.stream.Collectors;

public class FlightMapper {
    public static Flight ToEntity(FlightCreateRequest request) {
        return Flight.builder().number(request.number()).arrivalTime(request.arrivalTime())
                .departureTime(request.departureTime()).build();
    }

    //Check out this method, 'cause how can I get the seatsInventory of a flight when the flight doesn't have a collection of them?
    public static FlightResponse toResponse(Flight flight) {
        Set<TagRef> tagResponses = flight.getTags().stream()
                .map(tag -> new TagRef(tag.getId(), tag.getName()))
                .collect(Collectors.toSet());

        return new FlightResponse(
                flight.getId(),
                flight.getNumber(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getAirline() != null
                        ? new AirlineRef(flight.getAirline().getId(), flight.getAirline().getCode(), flight.getAirline().getName())
                        : null,
                flight.getOrigin() != null
                        ? new AirportRef(flight.getOrigin().getId(), flight.getOrigin().getCode(), flight.getOrigin().getCity())
                        : null,
                flight.getDestination() != null
                        ? new AirportRef(flight.getDestination().getId(), flight.getDestination().getCode(), flight.getDestination().getCity())
                        : null,
                tagResponses
        );
    }

    public static void patch(Flight entity, FlightUpdateRequest request ) {
        if (request.number() != null ) entity.setNumber(request.number());
        if (request.departureTime() != null ) entity.setDepartureTime(request.departureTime());
        if (request.arrivalTime() != null ) entity.setArrivalTime(request.arrivalTime());
    }

    public static void addTag(Flight flight, Tag tag) {
        flight.addTag(tag);
    }
}

