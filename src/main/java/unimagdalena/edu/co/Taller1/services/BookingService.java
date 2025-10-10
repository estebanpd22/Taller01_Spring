package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.BookingDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    BookingResponse create(BookingCreateRequest request);
    BookingResponse getById(Long id);

    Page<BookingResponse> listBookingsByPassengerEmailAndOrderedMostRecently(String passenger_email, Pageable pageable);
    Optional<BookingResponse> fetchGrpahById(Long id);

    BookingResponse update(Long id, Long passenger_id);
    void delete(Long id);
}