/*package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.api.dto.TagDtos.*;
import unimagdalena.edu.co.Taller1.entities.Tag;

@Mapper(
        componentModel = "spring",
        uses = {FlightMapperStruct.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TagMapperStruct {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flights", ignore = true)
    Tag toEntity(TagCreateRequest request);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flights", ignore = true)
    void updateEntity(@MappingTarget Tag tag, TagUpdateRequest request);

    @Mapping(target = "flights", source = "flights")
    TagResponse toResponse(Tag tag);
}
*/