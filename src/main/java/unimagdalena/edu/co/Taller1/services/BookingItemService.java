package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.BookingItemDtos.*;

import java.math.BigDecimal;
import java.util.List;

public interface BookingItemService {
    //Basic CRUD
    BookingItemResponse create(Long booking_id,BookingItemCreateRequest bookingItemCreateRequest);
    BookingItemResponse addBookingItem(Long booking_id, Long flight_id, BookingItemCreateRequest request);
    BookingItemResponse getBookingItemById(Long id);
    BookingItemResponse updateBookingItem(Long id, BookingItemUpdateRequest request);
    void deleteBookingItem(Long id);
    //-------------------------------------------------------//

    List<BookingItemResponse> listBookingItemsByBooking(Long booking_id);
    Long countReservedSeatsByFlightAndCabin(Long flight_id, String cabin);
    BigDecimal calculateTotalPriceByBooking(Long booking_id);
}