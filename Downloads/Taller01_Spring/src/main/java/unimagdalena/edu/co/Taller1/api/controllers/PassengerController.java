package unimagdalena.edu.co.Taller1.api.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos.*;
import unimagdalena.edu.co.Taller1.services.PassengerService;

public class PassengerController {
    private PassengerService passengerService;
    @PostMapping
    public ResponseEntity<PassengerResponse> createPassenger(@Valid @RequestBody PassengerCreateRequest request,
                                                                           UriComponentsBuilder uriBuilder) {
        var body = passengerService.create(request);
        var location = uriBuilder.path("/api/v1/passengers/{id}").buildAndExpand(body.id()).toUri();
        return ResponseEntity.created(location).body(body);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getPassenger(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getById(id));
    }
    @GetMapping(params = "email")
    public ResponseEntity<PassengerResponse> getPassengerByEmail(@RequestParam String email) {
        return ResponseEntity.ok(passengerService.getByEmail(email));
    }
    @GetMapping("/by-email-with-profile")
    public ResponseEntity<PassengerResponse> getPassengerWithProfile(@RequestParam String email) {
        return ResponseEntity.ok(passengerService.getPassengerWithProfile(email));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<PassengerResponse> updatePassenger(@PathVariable Long id,
                                                             @Valid@RequestBody PassengerUpdateRequest request){
        return ResponseEntity.ok(passengerService.update(id, request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<Page<PassengerResponse>> listAllPassengers(Pageable pageable) {
        var result = passengerService.listAllPassengers(pageable);
        return ResponseEntity.ok(result);
    }
}
