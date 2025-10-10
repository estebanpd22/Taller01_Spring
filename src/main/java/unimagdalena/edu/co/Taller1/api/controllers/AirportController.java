package unimagdalena.edu.co.Taller1.api.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import unimagdalena.edu.co.Taller1.api.dto.AirportDtos;
import unimagdalena.edu.co.Taller1.services.AirportService;

public class AirportController {
    private AirportService service;

    @PostMapping
    public ResponseEntity<AirportDtos.AirportResponse> create (@Valid @RequestBody AirportDtos.AirportCreateRequest req,
                                                               UriComponentsBuilder uriBuilder) {
        var body = service.create(req);
        var location = uriBuilder.path("/api/airlines/{id}").buildAndExpand(body.id()).toUri();

        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportDtos.AirportResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/by-code")
    public ResponseEntity<AirportDtos.AirportResponse> getByCode(@RequestParam String code) {
        return ResponseEntity.ok(service.getByCode(code));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AirportDtos.AirportResponse> update
            (@PathVariable Long id, @Valid @RequestBody AirportDtos.AirportUpdateRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
