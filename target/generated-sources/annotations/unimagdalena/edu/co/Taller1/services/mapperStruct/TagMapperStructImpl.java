package unimagdalena.edu.co.Taller1.services.mapperStruct;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import unimagdalena.edu.co.Taller1.api.dto.TagDtos;
import unimagdalena.edu.co.Taller1.domine.entities.Tag;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-02T23:32:09-0500",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class TagMapperStructImpl implements TagMapperStruct {

    @Override
    public Tag toEntity(TagDtos.TagCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Tag tag = new Tag();

        return tag;
    }

    @Override
    public TagDtos.TagResponse toResponse(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        TagDtos.TagResponse tagResponse = new TagDtos.TagResponse( id, name );

        return tagResponse;
    }

    @Override
    public TagDtos toRef(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        TagDtos tagDtos = new TagDtos();

        return tagDtos;
    }
}
