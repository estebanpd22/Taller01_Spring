package unimagdalena.edu.co.Taller1.domine.services.mapper;

import org.springframework.util.CollectionUtils;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos;
import unimagdalena.edu.co.Taller1.api.dto.TagDtos;
import unimagdalena.edu.co.Taller1.domine.entities.Tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class TagMapper {
    private final FlightMapper flightMapper;


    public TagMapper(FlightMapper flightMapper) {
        this.flightMapper = flightMapper;
    }

    public static Tag toEntity(TagDtos.TagCreateRequest request) {
        if (request == null) return null;

        return Tag.builder()
                .name(request.name())
                .flights(new HashSet<>())
                .build();
    }

    public static void updateEntity(Tag tag, TagDtos.TagUpdateRequest request) {
        if (request == null || tag == null) return;
        if (request.name() != null) tag.setName(request.name());
    }

    public TagDtos.TagResponse toResponse(Tag tag) {
        if (tag == null) return null;
        Collection<FlightDtos.FlightResponse> flightResponses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(tag.getFlights())) {
            flightResponses = tag.getFlights()
                    .stream()
                    .map(flightMapper::toResponse)
                    .toList();

        }

        return new TagDtos.TagResponse(
                tag.getId(),
                tag.getName(),
                new HashSet<>(flightResponses)

        );
    }
}
