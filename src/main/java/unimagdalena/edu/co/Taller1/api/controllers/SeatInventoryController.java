package unimagdalena.edu.co.Taller1.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import unimagdalena.edu.co.Taller1.entities.Cabin;
import unimagdalena.edu.co.Taller1.services.SeatInventoryService;
import unimagdalena.edu.co.Taller1.api.dto.SeatInventoryDtos.*;

import java.util.List;

@RestController
@RequestMapping("/api/seat-inventories")
@RequiredArgsConstructor
public class SeatInventoryController {

    private final SeatInventoryService seatInventoryService;

    // === CREATE ===
    @PostMapping
    public ResponseEntity<SeatInventoryResponse> create(@Valid @RequestBody SeatInventoryCreateRequest request,
                                                                          UriComponentsBuilder uriBuilder) {

        var body = seatInventoryService.create(request);
        var location = uriBuilder.path("/api/seat-inventories/{id}").buildAndExpand(body.id()).toUri();

        return ResponseEntity.created(location).body(body);
    }

    // === GET BY ID ===
    @GetMapping("/{id}")
    public ResponseEntity<SeatInventoryResponse> getById(@PathVariable Long id) {
        SeatInventoryResponse response = seatInventoryService.getById(id);
        return ResponseEntity.ok(response);
    }

    // === GET ALL ===
    @GetMapping
    public ResponseEntity<List<SeatInventoryResponse>> getAll() {
        List<SeatInventoryResponse> all = seatInventoryService.getAll();
        return ResponseEntity.ok(all);
    }

    // === UPDATE ===
    @PatchMapping("/{id}")
    public ResponseEntity<SeatInventoryResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody SeatInventoryUpdateRequest request) {
        SeatInventoryResponse updated = seatInventoryService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    // === DELETE ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seatInventoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // === FIND BY FLIGHT AND CABIN ===
    @GetMapping("/flight/{flightId}/cabin/{cabin}")
    public ResponseEntity<SeatInventoryResponse> findByFlightAndCabin(
            @PathVariable Long flightId,
            @PathVariable Cabin cabin) {
        SeatInventoryResponse response = seatInventoryService.findByFlightAndCabin(flightId, cabin);
        return ResponseEntity.ok(response);
    }

}
