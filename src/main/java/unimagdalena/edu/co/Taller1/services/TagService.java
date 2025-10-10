package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.TagDtos.*;

import java.util.Collection;
import java.util.List;

public interface TagService {
    //Basic CRUD
    TagResponse create(TagCreateRequest request);
    TagResponse getById(Long id);
    void delete(Long id);
    List<TagResponse> listAllTags();
    //------------------------------------//
    List<TagResponse> listTagsByNameIn(Collection<String> names);
}