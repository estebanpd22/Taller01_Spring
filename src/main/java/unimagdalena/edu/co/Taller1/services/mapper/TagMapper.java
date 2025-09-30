package unimagdalena.edu.co.Taller1.services.mapper;

import unimagdalena.edu.co.Taller1.api.dto.TagDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Tag;

public class TagMapper {
    public static Tag toEntity(TagCreateRequest request){
        return  Tag.builder().name(request.name()).build();
    }
    public static TagResponse toResponse(Tag tag){
        return new TagResponse(tag.getId(), tag.getName());
    }
}

