package unimagdalena.edu.co.Taller1.services.mapper;

import unimagdalena.edu.co.Taller1.domine.entities.*;

import unimagdalena.edu.co.Taller1.api.dto.BookingDtos.*;

import java.util.ArrayList;
import java.util.List;

public class BookingMapper {
    public static BookingResponse toResponse(Booking entity) {
        var items = entity.getItems() == null? List.<BookingItemResponse>of() : entity.getItems().stream().map(BookingMapper::toItemResponse).toList();
        var passengerName = entity.getPassenger() == null? null: entity.getPassenger().getFullName();
        var passengerEmail = entity.getPassenger() == null? null: entity.getPassenger().getEmail();

        return new BookingResponse(entity.getId(), entity.getCreatedAt(), passengerName, passengerEmail, items);
    }

    /*----------------------------------------------------------------------------------------------------*/
    //ToEntity method is service's responsibility

    public static BookingItemResponse toItemResponse(BookingItem entity) {
        return new BookingItemResponse(entity.getId(), entity.getCabin().name(), entity.getPrice(), entity.getSegmentOrder(), entity.getBooking().getId(),
                entity.getFlight().getId(), entity.getFlight().getNumber());
    }

    public static void itemPatch(BookingItem entity, BookingItemUpdateRequest request) {
        if (request.cabin() != null) entity.setCabin(Cabin.valueOf(request.cabin().toUpperCase()));
        if (request.price() != null) entity.setPrice(request.price());
        if (request.segmentOrder() != null) entity.setSegmentOrder(request.segmentOrder());
    }

    public static void addItem(BookingItem item, Booking booking){
        booking.getItems().add(item);
        item.setBooking(booking);
    }
}

