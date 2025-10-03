package unimagdalena.edu.co.Taller1.services.impl;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unimagdalena.edu.co.Taller1.domine.entities.BookingItem;
import unimagdalena.edu.co.Taller1.domine.entities.Cabin;
import unimagdalena.edu.co.Taller1.domine.repositories.BookingItemRepository;
import unimagdalena.edu.co.Taller1.domine.repositories.BookingRepository;
import unimagdalena.edu.co.Taller1.domine.repositories.FlightRepository;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.BookingItemService;
import unimagdalena.edu.co.Taller1.services.mapper.BookingMapper;
import unimagdalena.edu.co.Taller1.api.dto.BookingDtos.*;
import unimagdalena.edu.co.Taller1.services.mapperStruct.BookingMapperStruct;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingItemServiceImpl implements BookingItemService {
    private final BookingItemRepository bookingItemRepository;
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapperStruct bookingMapperStruct;

    @Override
    public BookingItemResponse addBookingItem(@Nonnull Long booking_id, @Nonnull Long flight_id, BookingItemCreateRequest request) {
        var flight =  flightRepository.findById(flight_id).orElseThrow(() -> new NotFoundException("Flight %d not found".formatted(flight_id)));
        var booking = bookingRepository.findById(booking_id).orElseThrow(
                () -> new NotFoundException("Booking %d not found".formatted(booking_id))
        );

        var bookingItem = BookingItem.builder().cabin(Cabin.valueOf(request.cabin())).price(request.price()).segmentOrder(request.segmentOrder())
                .flight(flight).booking(booking).build();
        booking.addItem(bookingItem);

        return bookingMapperStruct.toItemResponse(bookingItem);
    }

    @Override @Transactional(readOnly = true)
    public BookingItemResponse getBookingItem(@Nonnull Long id) {
        return bookingItemRepository.findById(id).map(bookingMapperStruct::toItemResponse).orElseThrow(
                () -> new NotFoundException("Booking Item %d not found".formatted(id))
        );
    }

    @Override
    public BookingItemResponse updateBookingItem(@Nonnull Long id, BookingItemUpdateRequest request, @Nonnull Long flight_id) {
        var bookingItem = bookingItemRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Booking Item %d not found".formatted(id))
        );
        bookingMapperStruct.itemPatch(request, bookingItem);

        var flight = flightRepository.findById(flight_id).orElseThrow(
                () -> new NotFoundException("Flight %d not found".formatted(flight_id))
        );
        bookingItem.setFlight(flight);

        return bookingMapperStruct.toItemResponse(bookingItem);
    }

    @Override
    public void deleteBookingItem(@Nonnull Long id) {
        bookingItemRepository.deleteById(id);
    }

    @Override @Transactional(readOnly = true)
    public List<BookingItemResponse> listBookingItemsByBooking(@Nonnull Long booking_id) {
        var booking = bookingRepository.findById(booking_id).orElseThrow(
                () -> new NotFoundException("Booking %d not found".formatted(booking_id))
        );
        return bookingItemRepository.findByBooking_IdOrderBySegmentOrder(booking.getId()).stream().map(bookingMapperStruct::toItemResponse).toList();
    }

    @Override @Transactional(readOnly = true)
    public Long countReservedSeatsByFlightAndCabin(@Nonnull Long flight_id, String cabin) {
        var flight = flightRepository.findById(flight_id).orElseThrow(
                () -> new NotFoundException("Flight %d not found".formatted(flight_id))
        );
        return bookingItemRepository.countSeatsByFlightAndCabin(flight.getId(), Cabin.valueOf(cabin));
    }

    @Override @Transactional(readOnly = true)
    public BigDecimal calculateTotalPriceByBooking(@Nonnull Long booking_id) {
        var booking = bookingRepository.findById(booking_id).orElseThrow(
                () -> new NotFoundException("Booking %d not found".formatted(booking_id))
        );
        return bookingItemRepository.calculateTotalPrice(booking.getId());
    }
}
