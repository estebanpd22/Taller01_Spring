package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.BookingItemDtos.*;

import java.math.BigDecimal;
import java.util.List;

public interface BookingItemService {
    BookingItemResponse createBookingItem(Long bookingId,BookingItemCreateRequest request);
    BookingItemResponse updateBookingItem(Long id,BookingItemUpdateRequest request);
    BookingItemResponse deleteBookingItem(long id);
    List<BookingItemResponse> findItemsByBooking(Long bookingId);

}