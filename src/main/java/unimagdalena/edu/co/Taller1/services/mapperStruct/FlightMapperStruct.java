package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Flight;
import unimagdalena.edu.co.Taller1.domine.entities.Tag;
import unimagdalena.edu.co.Taller1.services.mapper.TagMapper;

import javax.crypto.spec.PSource;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {AirlineMapperStruct.class, AirportMapperStruct.class, TagMapper.class})
public interface FlightMapperStruct {

    @Mapping(source = "airlineId", target = "airline")
    @Mapping(source = "originId", target = "origin")
    @Mapping(source = "destinationId", target = "destination")
    @Mapping(source = "tagIds", target = "tags")
    Flight toEntity(FlightCreateRequest request);

    @Mapping(source = "airline", target = "airlineId")
    @Mapping(source = "origin", target = "originId")
    @Mapping(source = "destination", target = "destinationId")
    @Mapping(source = "tags", target = "tagIds")
    FlightResponse toResponse(Flight flight);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(@MappingTarget Flight entity, FlightUpdateRequest request);

    default void addTag(Flight flight, Tag tag) {
        if (flight == null || tag == null) return;
        flight.addTag(tag);
    }
}
