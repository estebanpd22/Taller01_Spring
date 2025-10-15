package unimagdalena.edu.co.Taller1.api.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import unimagdalena.edu.co.Taller1.api.dto.BookingItemDtos.*;
import unimagdalena.edu.co.Taller1.services.BookingItemService;
import unimagdalena.edu.co.Taller1.services.BookingService;

import java.util.List;

public class BookingItemController {
    private BookingItemService service;
    @PostMapping
    public ResponseEntity<BookingItemResponse> create(@PathVariable Long bookingId,
                                                                      @Valid @RequestBody BookingItemCreateRequest request,
                                                                      UriComponentsBuilder uriBuilder){
        var body = service.create(bookingId, request);
        var location = uriBuilder.path("/api/bookings/{bookingId}/items/{itemId}").
                buildAndExpand(bookingId,body.id()).toUri();
        return ResponseEntity.created(location).body(body);
    }
    @GetMapping
    public ResponseEntity<List<BookingItemResponse>> list(@PathVariable Long bookingId){
        return ResponseEntity.ok(service.listBookingItemsByBooking(bookingId));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<BookingItemResponse> update(@PathVariable Long id,
                                                                      @Valid@RequestBody BookingItemUpdateRequest request){
        return ResponseEntity.ok(service.updateBookingItem(id,request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteBookingItem(id);
        return ResponseEntity.noContent().build();
    }
}
