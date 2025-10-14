package unimagdalena.edu.co.Taller1.services.mapper;

import org.springframework.util.CollectionUtils;
import unimagdalena.edu.co.Taller1.api.dto.TagDtos.*;
import unimagdalena.edu.co.Taller1.entities.Tag;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class TagMapper {

    public static Tag toEntity(TagCreateRequest request) {
        if (request == null) return null;

        return Tag.builder()
                .name(request.name())
                .flights(new HashSet<>())
                .build();
    }
    public static void updateEntity(Tag tag, TagUpdateRequest request) {
        if (request == null || tag == null) return;
        if (request.name() != null) tag.setName(request.name());
    }

    public static TagResponse toResponse(Tag tag) {
        if (tag == null) return null;
        Collection<FlightResponse> flightResponses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(tag.getFlights())) {
            flightResponses = tag.getFlights()
                    .stream()
                    .map(FlightMapper::toResponse)
                    .toList();

        }
        return new TagResponse(
                tag.getId(),
                tag.getName(),
                flightResponses
        );
    }
}
