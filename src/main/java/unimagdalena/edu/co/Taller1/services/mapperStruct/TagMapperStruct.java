package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.Mapper;
import unimagdalena.edu.co.Taller1.api.dto.TagDtos;
import unimagdalena.edu.co.Taller1.api.dto.TagDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Tag;

@Mapper(componentModel = "spring")
public interface TagMapperStruct {

    Tag toEntity(TagCreateRequest request);

    TagResponse toResponse(Tag tag);
    TagDtos toRef(Tag tag);
}
