package unimagdalena.edu.co.Taller1.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import unimagdalena.edu.co.Taller1.api.dto.AirportDtos;
import unimagdalena.edu.co.Taller1.services.AirportService;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
@Validated
public class AirportController {

    private final AirportService service;

    @PostMapping
    public ResponseEntity<AirportDtos.AirportResponse> create (@Valid @RequestBody AirportDtos.AirportCreateRequest req,
                                                               UriComponentsBuilder uriBuilder) {
        var body = service.create(req);
        var location = uriBuilder.path("/api/airports/{id}").buildAndExpand(body.id()).toUri();

        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportDtos.AirportResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/by-city")
    public ResponseEntity<List<AirportDtos.AirportResponse>> getCityAirports(@RequestParam String city) {
        return ResponseEntity.ok(service.cityList(city));
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

    @GetMapping
    public ResponseEntity<Page<AirportDtos.AirportResponse>> airportList(Pageable pageable) {
        var result = service.airportList(pageable);
        return ResponseEntity.ok(result);
    }
}
