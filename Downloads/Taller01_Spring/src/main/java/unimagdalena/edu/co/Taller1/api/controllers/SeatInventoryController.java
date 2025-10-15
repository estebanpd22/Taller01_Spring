package unimagdalena.edu.co.Taller1.api.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import unimagdalena.edu.co.Taller1.api.dto.SeatInventoryDtos.*;
import unimagdalena.edu.co.Taller1.services.SeatInventoryService;

import java.util.List;

public class SeatInventoryController {
    private SeatInventoryService seatService;

    @PostMapping
    public ResponseEntity<SeatInventoryResponse> create(@PathVariable Long flightId, @Valid @RequestBody
    SeatInventoryCreateRequest request, UriComponentsBuilder uriBuilder) {
        var body = seatService.create(flightId, request);
        var location = uriBuilder.path("/api/v1/flights/{flightId}/seatInventories/{seatInventoryId}")
                .buildAndExpand(flightId, body.id()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{seatInventoryId}")
    public ResponseEntity<SeatInventoryResponse> get(@PathVariable Long flightId, @PathVariable Long seatInventoryId) {
        return ResponseEntity.ok(seatService.getById(seatInventoryId));
    }

    @GetMapping
    public ResponseEntity<List<SeatInventoryResponse>> listByFlight(@PathVariable Long flightId) {
        return ResponseEntity.ok(seatService.listSeatInventoriesByFlight(flightId));
    }

    @GetMapping(params = "cabin")
    public ResponseEntity<SeatInventoryResponse> getByFlightAndCabin(@PathVariable Long flightId,
                                                                     @RequestParam String cabin) {
        return ResponseEntity.ok(seatService.getSeatInventoryByFlightAndCabin(flightId, cabin));
    }

    @PatchMapping("/{seatInventoryId}")
    public ResponseEntity<SeatInventoryResponse> patch(@PathVariable Long flightId, @PathVariable Long seatInventoryId,
                                                       @Valid @RequestBody SeatInventoryUpdateRequest request) {
        return ResponseEntity.ok(seatService.update(seatInventoryId, request));
    }


    @DeleteMapping("/{seatInventoryId}")
    public ResponseEntity<Void> delete(@PathVariable Long flightId, @PathVariable Long seatInventoryId) {
        seatService.delete(seatInventoryId);
        return ResponseEntity.noContent().build();
    }
}
