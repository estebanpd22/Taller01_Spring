package unimagdalena.edu.co.Taller1.api.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class BookingDtos {
    public  record BookingCreateRequest(OffsetDateTime createdAt, Long passengerId ) implements Serializable {}
    public  record BookingUpdateRequest(Long id, Long passenger_id) implements Serializable {}
    public  record BookingResponse(Long id, OffsetDateTime createdAt, PassengerDtos.PassengerResponse passengerDto) implements Serializable {}
}
