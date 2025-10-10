package unimagdalena.edu.co.Taller1.services.impl;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unimagdalena.edu.co.Taller1.api.dto.BookingItemDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.BookingItem;
import unimagdalena.edu.co.Taller1.domine.entities.Cabin;
import unimagdalena.edu.co.Taller1.domine.repositories.BookingItemRepository;
import unimagdalena.edu.co.Taller1.domine.repositories.BookingRepository;
import unimagdalena.edu.co.Taller1.domine.repositories.FlightRepository;
import unimagdalena.edu.co.Taller1.domine.repositories.SeatInventoryRepository;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.BookingItemService;
import unimagdalena.edu.co.Taller1.api.dto.BookingDtos.*;
import unimagdalena.edu.co.Taller1.services.mapperStruct.BookingItemMapperStruct;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingItemServiceImpl implements BookingItemService {
    private final BookingItemRepository bookingItemRepository;
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final BookingItemMapperStruct mapper;
    private final SeatInventoryRepository seatInventoryRepository;

    @Override
    public BookingItemResponse create(Long bookingId, BookingItemCreateRequest request) {
        var booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Booking not found"));
        var flight = flightRepository.findById(request.flightId()).orElseThrow(() -> new NotFoundException("Flight not found"));
        var seats = seatInventoryRepository.findByFlight_IdAndCabin(request.flightId(), Cabin.valueOf(request.cabin())).orElseThrow(() -> new NotFoundException("Seats not found"));

        if (seats.getAvailableSeats() <1){
            throw new IllegalStateException("Not enough seats available");
        }
        seats.setAvailableSeats(seats.getAvailableSeats() - 1);
        seatInventoryRepository.save(seats);

        //creando bookingItem
        BookingItem bookingItem = mapper.toEntity(request);
        bookingItem.setBooking(booking);

        BookingItem bookingItemSaved = bookingItemRepository.save(bookingItem);
        return  mapper.toItemResponse(bookingItemSaved);
    }

    @Override
    public BookingItemResponse addBookingItem(@Nonnull Long booking_id, @Nonnull Long flight_id, BookingItemCreateRequest request) {
        var flight =  flightRepository.findById(flight_id).orElseThrow(() -> new NotFoundException("Flight %d not found".formatted(flight_id)));
        var booking = bookingRepository.findById(booking_id).orElseThrow(
                () -> new NotFoundException("Booking %d not found".formatted(booking_id))
        );

        var bookingItem = BookingItem.builder().cabin(Cabin.valueOf(request.cabin())).price(request.price()).segmentOrder(request.segmentOrder())
                .flight(flight).booking(booking).build();

        return mapper.toItemResponse(bookingItem);
    }

    @Override @Transactional(readOnly = true)
    public BookingItemResponse getBookingItemById(@Nonnull Long id) {
        return bookingItemRepository.findById(id).map(mapper::toItemResponse).orElseThrow(
                () -> new NotFoundException("Booking Item %d not found".formatted(id))
        );
    }

    @Override
    public BookingItemResponse updateBookingItem(@Nonnull Long id, BookingItemUpdateRequest request) {
        var bookingItem = bookingItemRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Booking Item %d not found".formatted(id))
        );
        mapper.itemPatch(request, bookingItem);

        var flight = flightRepository.findById(flight_id).orElseThrow(
                () -> new NotFoundException("Flight %d not found".formatted(flight_id))
        );
        bookingItem.setFlight(flight);
        mapper.itemPatch(request, );
        return mapper.toItemResponse(bookingItem);
    }

    @Override
    public void deleteBookingItem(@Nonnull Long id) {
        bookingItemRepository.deleteById(id);
    }

    @Override
    public List<BookingItemResponse> listBookingItemsByBooking(Long booking_id) {
        return List.of();
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
