package unimagdalena.edu.co.Taller1.api.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import unimagdalena.edu.co.Taller1.api.dto.TagDtos.*;
import unimagdalena.edu.co.Taller1.services.TagService;

import java.util.List;

public class TagController {
    private TagService tagService;

    @PostMapping
    public ResponseEntity<TagResponse> create(@Valid @RequestBody TagCreateRequest request, UriComponentsBuilder uriBuilder) {
        var body = tagService.create(request);
        var location = uriBuilder.path("/api/v1/tags/{id}").buildAndExpand(body.id()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> list() {
        return ResponseEntity.ok(tagService.listAllTags());
    }

    @GetMapping(params = "names") //RequestParam is made mostly for filtering info
    public ResponseEntity<List<TagResponse>> listByNames(@RequestParam List<String> names) {
        return ResponseEntity.ok(tagService.listTagsByNameIn(names));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
