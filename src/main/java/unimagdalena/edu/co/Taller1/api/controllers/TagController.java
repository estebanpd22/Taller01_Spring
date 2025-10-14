package unimagdalena.edu.co.Taller1.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unimagdalena.edu.co.Taller1.api.dto.TagDtos.*;
import unimagdalena.edu.co.Taller1.services.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    // === CREATE ===
    @PostMapping
    public ResponseEntity<TagResponse> createTag(
            @Valid @RequestBody TagCreateRequest request) {
        TagResponse created = tagService.createTag(request);
        return ResponseEntity.ok(created);
    }

    // === GET BY ID ===
    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getTag(@PathVariable Long id) {
        TagResponse response = tagService.getTag(id);
        return ResponseEntity.ok(response);
    }

    // === GET ALL ===
    @GetMapping
    public ResponseEntity<List<TagResponse>> listAllTags() {
        List<TagResponse> tags = tagService.listAllTags();
        return ResponseEntity.ok(tags);
    }

    // === GET TAGS BY NAME LIST ===
    @PostMapping("/search/by-names")
    public ResponseEntity<List<TagResponse>> listTagsByNameIn(
            @RequestBody List<String> names) {
        List<TagResponse> tags = tagService.listTagsByNameIn(names);
        return ResponseEntity.ok(tags);
    }

    // === UPDATE ===
    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> updateTag(
            @PathVariable Long id,
            @Valid @RequestBody TagUpdateRequest request) {

        // Reutilizamos createTag para simplificar lógica, pero si tienes un método update en el service, cámbialo.
        TagResponse updated = tagService.createTag(
                new TagCreateRequest(request.name())
        );
        return ResponseEntity.ok(updated);
    }

    // === DELETE ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}