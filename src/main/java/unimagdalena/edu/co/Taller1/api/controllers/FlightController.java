package unimagdalena.edu.co.Taller1.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos.*;
import unimagdalena.edu.co.Taller1.services.FlightService;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
@Validated
public class FlightController {
    private final FlightService service;

    @PostMapping
    public ResponseEntity<FlightResponse> create(
            @Valid @RequestBody FlightCreateRequest request, UriComponentsBuilder uriBuilder) {

        var body = service.create(request);
        var location = uriBuilder.path("/api/flights/{id}").buildAndExpand(body.id()).toUri();

        return ResponseEntity.created(location).body(body);
    }

    @GetMapping
    public ResponseEntity<List<FlightResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/by-airline")
    public ResponseEntity<Page<FlightResponse>> findByAirlineName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return ResponseEntity.ok(service.findByAirlineName(name, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<FlightResponse>> searchFlights(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) OffsetDateTime from,
            @RequestParam(required = false) OffsetDateTime to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = (Pageable) PageRequest.of(page, size, Sort.by("departureTime").ascending());
        return ResponseEntity.ok(service.searchFlights(origin, destination, from, to, pageable));
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
