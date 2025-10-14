package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.TagDtos.*;

import java.util.Collection;
import java.util.List;

public interface TagService {
    TagResponse createTag(TagCreateRequest request);
    TagResponse getTag(Long id);
    void deleteTag(Long id);
    List<TagResponse> listAllTags();
    List<TagResponse> listTagsByNameIn(List<String> names);
}