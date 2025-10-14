package unimagdalena.edu.co.Taller1.api.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public class BookingDtos {
    public  record BookingCreateRequest(@NotNull OffsetDateTime createdAt, @NotNull Long passengerId, List<BookingItemDtos.BookingItemCreateRequest> items) implements Serializable {}
    public  record BookingUpdateRequest(Long passenger_id) implements Serializable {}
    public  record BookingResponse(Long id, OffsetDateTime createdAt, PassengerDtos.PassengerResponse passengerDto,  List<BookingItemDtos.BookingItemResponse> items) implements Serializable {}
}

