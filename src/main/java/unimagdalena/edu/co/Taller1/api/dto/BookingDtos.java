package unimagdalena.edu.co.Taller1.api.dto;

import jakarta.annotation.Nonnull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class BookingDtos {
    public record BookingCreateRequest(@Nonnull Long passenger_id) implements Serializable {}

    public record BookingResponse(Long id, OffsetDateTime createdAt, String passenger_name, String passenger_email,
                                  List<BookingItemDtos.BookingItemResponse> items) implements Serializable{}

    public record BookingUpdateRequest(Long passenger_id)  implements Serializable {}

}
