package unimagdalena.edu.co.Taller1.services.impl;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unimagdalena.edu.co.Taller1.domine.repositories.TagRepository;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.TagService;
import unimagdalena.edu.co.Taller1.api.dto.TagDtos.*;
import unimagdalena.edu.co.Taller1.services.mapperStruct.TagMapperStruct;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapperStruct tagMapperStruct;

    @Override
    public TagResponse createTag(TagCreateRequest request) {
        var tag = tagMapperStruct.toEntity(request);
        return tagMapperStruct.toResponse(tagRepository.save(tag));
    }

    @Override
    public TagResponse getTag(Long id) {
        return tagRepository.findById(id).map(tagMapperStruct::toResponse).orElseThrow(() -> new NotFoundException("Tag %d not found".formatted(id)));
    }

    @Override
    public void deleteTag(@Nonnull Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<TagResponse> listAllTags() {
        return tagRepository.findAll().stream().map(tagMapperStruct::toResponse).toList();
    }

    @Override
    public List<TagResponse> listTagsByNameIn(Collection<String> names) {
        return tagRepository.findByNameIn(names).stream().map(tagMapperStruct::toResponse).toList();
    }
}