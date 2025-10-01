package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Flight;
import unimagdalena.edu.co.Taller1.domine.entities.Tag;
import unimagdalena.edu.co.Taller1.services.mapper.TagMapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {AirlineMapperStruct.class, AirportMapperStruct.class, TagMapper.class})
public interface FlightMapperStruct {

    default Flight toEntity(FlightDtos.FlightCreateRequest request) {
        if (request == null) return null;
        return Flight.builder()
                .number(request.number())
                .arrivalTime(request.arrivalTime())
                .departureTime(request.departureTime())
                .build();
    }

    @Mapping(target = "airline", source = "airline")
    @Mapping(target = "origin", source = "origin")
    @Mapping(target = "destination", source = "destination")
    @Mapping(target = "tags", source = "tags")
    FlightDtos.FlightResponse toResponse(Flight flight);

    void patch(@MappingTarget Flight entity, FlightDtos.FlightUpdateRequest request);

    default void addTag(Flight flight, Tag tag) {
        if (flight == null || tag == null) return;
        flight.addTag(tag);
    }
}
