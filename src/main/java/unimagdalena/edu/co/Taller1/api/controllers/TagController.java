package unimagdalena.edu.co.Taller1.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import unimagdalena.edu.co.Taller1.api.dto.TagDtos.*;
import unimagdalena.edu.co.Taller1.services.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Validated
public class TagController {

    private final TagService tagService;

    // === CREATE ===
    @PostMapping
    public ResponseEntity<TagResponse> create(
            @Valid @RequestBody TagCreateRequest request, UriComponentsBuilder uriBuilder) {

        var body = tagService.createTag(request);
        var location = uriBuilder.path("/api/tags/{id}").buildAndExpand(body.id()).toUri();

        return ResponseEntity.created(location).body(body);
    }

    // === GET BY ID ===
    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getById(@PathVariable Long id) {
        TagResponse response = tagService.getTag(id);
        return ResponseEntity.ok(response);
    }

    // === GET ALL ===
    @GetMapping
    public ResponseEntity<List<TagResponse>> listAll() {
        List<TagResponse> tags = tagService.listAllTags();
        return ResponseEntity.ok(tags);
    }

    // === GET TAGS BY NAME LIST ===
    @GetMapping("/by-names")
    public ResponseEntity<List<TagResponse>> listByNameIn(
            @RequestParam List<String> names) {
        return ResponseEntity.ok(tagService.listTagsByNameIn(names));
    }



    // === DELETE ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}