package unimagdalena.edu.co.Taller1.services.impl;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unimagdalena.edu.co.Taller1.entities.Tag;
import unimagdalena.edu.co.Taller1.repositories.TagRepository;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.TagService;
import unimagdalena.edu.co.Taller1.api.dto.TagDtos.*;
import unimagdalena.edu.co.Taller1.services.mapper.TagMapper;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public TagResponse createTag(TagCreateRequest request) {
        if (request == null) throw new IllegalArgumentException("TagCreateRequest cannot be null");
        if (request.name() == null || request.name().isBlank()) {
            throw new IllegalArgumentException("Tag name cannot be null or blank");
        }

        var tag = TagMapper.toEntity(request);
        return TagMapper.toResponse(tagRepository.save(tag));
    }

    @Override
    @Transactional(readOnly = true)
    public TagResponse getTag(Long id) {
        return tagRepository.findById(id).map(TagMapper::toResponse).orElseThrow(() -> new NotFoundException("Tag % not found".formatted(id)));
    }

    @Override
    public void deleteTag(@Nonnull Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tag not found with id: " + id));
        tagRepository.delete(tag);

    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> listAllTags() {
        List<TagResponse> tags = tagRepository.findAll().stream()
                .map(TagMapper::toResponse)
                .toList();

        if (tags.isEmpty()) {
            throw new NotFoundException("No tags found");
        }

        return tags;  }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> listTagsByNameIn(List<String> names) {
        if (names == null || names.isEmpty()) {
            throw new IllegalArgumentException("Tag names list cannot be null or empty");
        }

        List<TagResponse> tags = tagRepository.findByNameIn(names).stream()
                .map(TagMapper::toResponse)
                .toList();

        if (tags.isEmpty()) {
            throw new NotFoundException("No tags found for given names: " + names);
        }

        return tags; }
}