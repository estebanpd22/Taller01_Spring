package unimagdalena.edu.co.Taller1.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unimagdalena.edu.co.Taller1.entities.Cabin;
import unimagdalena.edu.co.Taller1.services.SeatInventoryService;
import unimagdalena.edu.co.Taller1.api.dto.SeatInventoryDtos.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seat-inventories")
@RequiredArgsConstructor
public class SeatInventoryController {

    private final SeatInventoryService seatInventoryService;

    // === CREATE ===
    @PostMapping
    public ResponseEntity<SeatInventoryResponse> createSeatInventory(
            @Valid @RequestBody SeatInventoryCreateRequest request) {
        SeatInventoryResponse created = seatInventoryService.create(request);
        return ResponseEntity.ok(created);
    }

    // === GET BY ID ===
    @GetMapping("/{id}")
    public ResponseEntity<SeatInventoryResponse> getSeatInventoryById(@PathVariable Long id) {
        SeatInventoryResponse response = seatInventoryService.getById(id);
        return ResponseEntity.ok(response);
    }

    // === GET ALL ===
    @GetMapping
    public ResponseEntity<List<SeatInventoryResponse>> getAllSeatInventories() {
        List<SeatInventoryResponse> all = seatInventoryService.getAll();
        return ResponseEntity.ok(all);
    }

    // === UPDATE ===
    @PutMapping("/{id}")
    public ResponseEntity<SeatInventoryResponse> updateSeatInventory(
            @PathVariable Long id,
            @Valid @RequestBody SeatInventoryUpdateRequest request) {
        SeatInventoryResponse updated = seatInventoryService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    // === DELETE ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeatInventory(@PathVariable Long id) {
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

    // === CHECK AVAILABLE SEATS ===
    @GetMapping("/flight/{flightId}/cabin/{cabin}/has-available/{minSeats}")
    public ResponseEntity<Boolean> hasAvailableSeats(
            @PathVariable Long flightId,
            @PathVariable Cabin cabin,
            @PathVariable Integer minSeats) {
        boolean hasSeats = seatInventoryService.hasAvailableSeats(flightId, cabin, minSeats);
        return ResponseEntity.ok(hasSeats);
    }

    // === DECREMENT SEATS ===
    @PostMapping("/flight/{flightId}/cabin/{cabin}/decrement/{quantity}")
    public ResponseEntity<Boolean> decrementAvailableSeats(
            @PathVariable Long flightId,
            @PathVariable Cabin cabin,
            @PathVariable int quantity) {
        boolean result = seatInventoryService.decrementAvailableSeats(flightId, cabin, quantity);
        return ResponseEntity.ok(result);
    }

    // === INCREMENT SEATS ===
    @PostMapping("/flight/{flightId}/cabin/{cabin}/increment/{quantity}")
    public ResponseEntity<Boolean> incrementAvailableSeats(
            @PathVariable Long flightId,
            @PathVariable Cabin cabin,
            @PathVariable int quantity) {
        boolean result = seatInventoryService.incrementAvailableSeats(flightId, cabin, quantity);
        return ResponseEntity.ok(result);
    }
}
