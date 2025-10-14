package unimagdalena.edu.co.Taller1.services.mapper;

import org.springframework.util.CollectionUtils;
import unimagdalena.edu.co.Taller1.api.dto.BookingDtos.*;
import unimagdalena.edu.co.Taller1.api.dto.BookingItemDtos.*;
import unimagdalena.edu.co.Taller1.entities.Booking;
import unimagdalena.edu.co.Taller1.entities.BookingItem;
import unimagdalena.edu.co.Taller1.entities.Passenger;

import java.util.ArrayList;
import java.util.List;

public class BookingMapper {

    public static Booking toEntity(BookingCreateRequest request, Passenger passenger, List<BookingItem> items) {
        if (request == null) return null;
        if (passenger == null) throw  new IllegalArgumentException("Passenger is null");

        Booking booking = Booking.builder()
                .createdAt(request.createdAt())
                .passenger(passenger)
                .items(new ArrayList<>())
                .build();

        if (!CollectionUtils.isEmpty(items)) {
            items.forEach(booking::addItem);
        }
        return booking;
    }

    public static void updateEntity(Booking booking, Passenger passenger){
        if (booking == null) return;

        if (passenger != null) booking.setPassenger(passenger);
    }

    public static BookingResponse toResponse(Booking booking) {
        if (booking == null) return null;

        List<BookingItemResponse> itemsResponse =
                CollectionUtils.isEmpty(booking.getItems())?
                        new ArrayList<>() :
                        booking.getItems().stream()
                                .map(BookingItemMapper:: toResponse)
                                .toList();
        return new BookingResponse(
                booking.getId(),
                booking.getCreatedAt(),
                PassengerMapper.toResponse(booking.getPassenger()),
                itemsResponse
        );

    }
}

