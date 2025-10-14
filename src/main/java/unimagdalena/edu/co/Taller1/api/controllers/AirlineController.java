package unimagdalena.edu.co.Taller1.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import unimagdalena.edu.co.Taller1.api.dto.AirlineDtos;
import unimagdalena.edu.co.Taller1.services.AirlineService;

@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
@Validated
public class AirlineController {

    private final AirlineService service;

    @PostMapping
    public ResponseEntity<AirlineDtos.AirlineResponse> create (@Valid @RequestBody AirlineDtos.AirlineCreateRequest req,
                                                               UriComponentsBuilder uriBuilder) {
        var body = service.create(req);
        var location = uriBuilder.path("/api/airlines/{id}").buildAndExpand(body.id()).toUri();

        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineDtos.AirlineResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/by-code")
    public ResponseEntity<AirlineDtos.AirlineResponse> getByCode(@RequestParam String code) {
        return ResponseEntity.ok(service.getByCode(code));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AirlineDtos.AirlineResponse> update
            (@PathVariable Long id, @Valid @RequestBody AirlineDtos.AirlineUpdateRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
