package unimagdalena.edu.co.Taller1.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unimagdalena.edu.co.Taller1.api.dto.BookingItemDtos.*;
import unimagdalena.edu.co.Taller1.entities.*;
import unimagdalena.edu.co.Taller1.repositories.BookingItemRepository;
import unimagdalena.edu.co.Taller1.repositories.BookingRepository;
import unimagdalena.edu.co.Taller1.repositories.FlightRepository;
import unimagdalena.edu.co.Taller1.repositories.SeatInventoryRepository;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.BookingItemService;
import unimagdalena.edu.co.Taller1.services.mapper.BookingItemMapper;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingItemServiceImpl  implements BookingItemService {

    private final BookingItemRepository bookingItemRepository;
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final SeatInventoryRepository seatInventoryRepository;
    private final BookingItemMapper bookingItemMapper;

    @Override
    public BookingItemResponse createBookingItem(Long bookingId, BookingItemCreateRequest request) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Booking not found"));

        Flight flight = flightRepository.findById(request.flighId()).orElseThrow(() -> new NotFoundException("Flight not found"));

        //reservando asiento
        SeatInventory inventory = seatInventoryRepository.findByFlight_IdAndCabin(flight.getId(), request.cabin()).orElseThrow(() -> new NotFoundException("Seat inventory not found"));
        if (inventory.getAvailableSeats() <1){
            throw new IllegalStateException("Not enough seats available");
        }
        inventory.setAvailableSeats(inventory.getAvailableSeats() - 1);
        seatInventoryRepository.save(inventory);

        //creando bookingItem
        BookingItem bookingItem = BookingItemMapper.toEntity(request,flight);
        bookingItem.setBooking(booking);

        BookingItem bookingItemSaved = bookingItemRepository.save(bookingItem);
        return  bookingItemMapper.toResponse(bookingItemSaved);

    }

    @Override
    public BookingItemResponse updateBookingItem(Long id,BookingItemUpdateRequest request) {

        BookingItem bookingItem = bookingItemRepository.findById(id).orElseThrow(() -> new NotFoundException("Booking not found"));

        Flight flight= new Flight();
        if (request != null){
            flight= flightRepository.findById(request.flight_id()).orElseThrow(() -> new NotFoundException("Flight not found"));}

        BookingItemMapper.updateEntity(bookingItem,request,flight);
        BookingItem bookingItemUpdate = bookingItemRepository.save(bookingItem);

        return BookingItemMapper.toResponse(bookingItemUpdate);
    }

    @Override
    public BookingItemResponse deleteBookingItem(long id) {
        BookingItem bookingItem =  bookingItemRepository.findById(id).orElseThrow(() -> new NotFoundException("Booking not found"));

        SeatInventory inventory = seatInventoryRepository.findByFlight_IdAndCabin(bookingItem.getFlight().getId(), bookingItem.getCabin()).orElseThrow(() -> new NotFoundException("Seat inventory not found"));

        inventory.setAvailableSeats(inventory.getAvailableSeats() - 1);
        seatInventoryRepository.save(inventory);

        bookingItemRepository.delete(bookingItem);

        return BookingItemMapper.toResponse(bookingItem);
    }

    @Override
    public List<BookingItemResponse> findItemsByBooking(Long bookingId) {
        List<BookingItem> bookingItems = bookingItemRepository.findByBooking_IdOrderBySegmentOrder(bookingId);
        if (bookingItems.isEmpty()){
            throw new NotFoundException("Not items found for this booking");
        }
        return bookingItems.stream().map(BookingItemMapper::toResponse).toList();
    }
}
