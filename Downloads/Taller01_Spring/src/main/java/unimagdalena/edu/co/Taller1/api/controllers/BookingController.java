package unimagdalena.edu.co.Taller1.api.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import unimagdalena.edu.co.Taller1.api.dto.BookingDtos;
import unimagdalena.edu.co.Taller1.services.BookingService;

import java.util.List;

public class BookingController {
    private BookingService service;

    @PostMapping
    public ResponseEntity<BookingDtos.BookingResponse> create(@Valid @RequestBody BookingDtos.BookingCreateRequest request,
                                                              UriComponentsBuilder uriBuilder){
        var body = service.create(request);
        var location = uriBuilder.path("/api/bookings/{id}").buildAndExpand(body.id()).toUri();
        return ResponseEntity.created(location).body(body);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookingDtos.BookingResponse> get(@PathVariable long id){
        return ResponseEntity.ok(service.getById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<BookingDtos.BookingResponse> update(@PathVariable long id,
                                                              @Valid  @RequestBody BookingDtos.BookingUpdateRequest request){
        return ResponseEntity.ok(service.update(id, request));
    }

}
