package unimagdalena.edu.co.Taller1.services.mapper;

import unimagdalena.edu.co.Taller1.entities.BookingItem;
import unimagdalena.edu.co.Taller1.api.dto.BookingItemDtos.*;
import unimagdalena.edu.co.Taller1.entities.Flight;

public class BookingItemMapper {

    public static BookingItem toEntity(BookingItemCreateRequest request, Flight flight) {
        if (request == null) return null;
        if (flight == null) throw new IllegalArgumentException("flight is null");

        return BookingItem.builder()
                .price(request.price())
                .segmentOrder(request.segmentOrder())
                .cabin(request.cabin())
                .flight(flight)
                .build();
    }

    public static void updateEntity(BookingItem item, BookingItemUpdateRequest request, Flight flight) {
        if (item == null || request == null ) return;
        if (request.price() != null) {
            item.setPrice(request.price());
        }
        if (request.segmentOrder() != null) {
            item.setSegmentOrder(request.segmentOrder());
        }
        if (request.cabin() != null) {
            item.setCabin(request.cabin());
        }
        if (flight != null) {
            item.setFlight(flight);
        }

    }

    public static  BookingItemResponse toResponse(BookingItem item) {
        if (item == null) return null;
        return new BookingItemResponse(
                item.getId(), item.getPrice(), item.getSegmentOrder(),
                item.getCabin(), FlightMapper.toResponse(item.getFlight())
        );

    }
}