package unimagdalena.edu.co.Taller1.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos.*;
import unimagdalena.edu.co.Taller1.services.PassengerService;

@RestController
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    // === CREATE ===
    @PostMapping
    public ResponseEntity<PassengerResponse> createPassenger(
            @Valid @RequestBody PassengerCreateRequest request) {
        PassengerResponse created = passengerService.createPassenger(request);
        return ResponseEntity.ok(created);
    }

    // === GET BY ID ===
    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getPassengerById(@PathVariable Long id) {
        PassengerResponse response = passengerService.get(id);
        return ResponseEntity.ok(response);
    }

    // === GET BY EMAIL ===
    @GetMapping("/email/{email}")
    public ResponseEntity<PassengerResponse> getPassengerByEmail(@PathVariable String email) {
        PassengerResponse response = passengerService.getByEmail(email);
        return ResponseEntity.ok(response);
    }

    // === GET BY EMAIL WITH PROFILE ===
    @GetMapping("/email/{email}/profile")
    public ResponseEntity<PassengerResponse> getPassengerByEmailWithProfile(@PathVariable String email) {
        PassengerResponse response = passengerService.getByEmailWithProfile(email);
        return ResponseEntity.ok(response);
    }

    // === UPDATE ===
    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponse> updatePassenger(
            @PathVariable Long id,
            @Valid @RequestBody PassengerUpdateRequest request) {
        PassengerResponse updated = passengerService.updatePassenger(id, request);
        return ResponseEntity.ok(updated);
    }

    // === DELETE ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
