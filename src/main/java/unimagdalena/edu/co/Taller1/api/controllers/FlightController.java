package unimagdalena.edu.co.Taller1.api.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos.*;
import unimagdalena.edu.co.Taller1.services.FlightService;


public class FlightController {
    private FlightService service;

    @PostMapping
    public ResponseEntity<FlightResponse> create(
            @Valid @RequestBody FlightCreateRequest request, UriComponentsBuilder uriBuilder) {

        var body = service.create(request);
        var location = uriBuilder.path("/api/flights/{id}").buildAndExpand(body.id()).toUri();

        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<FlightResponse> update(@PathVariable Long id,
                                                            @Valid @RequestBody FlightUpdateRequest request) {

        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
