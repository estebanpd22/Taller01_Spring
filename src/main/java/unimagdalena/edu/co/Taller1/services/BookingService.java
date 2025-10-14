package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.BookingDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    BookingResponse createBooking(BookingCreateRequest request);
    BookingResponse updateBooking(Long id,BookingUpdateRequest request);
    BookingResponse getBookingId(long id);
    void deleteById(long id);
    List<BookingResponse> finBookingByPassengerEmail(String email);
    BookingResponse getBookingWithDetails(long id);
}