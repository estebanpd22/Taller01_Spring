package unimagdalena.edu.co.Taller1.services.mapperStruct;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import unimagdalena.edu.co.Taller1.api.dto.BookingDtos;
import unimagdalena.edu.co.Taller1.domine.entities.Booking;
import unimagdalena.edu.co.Taller1.domine.entities.BookingItem;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-03T14:00:05-0500",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class BookingMapperStructImpl implements BookingMapperStruct {

    @Override
    public BookingDtos.BookingResponse toResponse(Booking entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        OffsetDateTime createdAt = null;
        List<BookingDtos.BookingItemResponse> items = null;

        id = entity.getId();
        createdAt = entity.getCreatedAt();
        items = toItemResponseList( entity.getItems() );

        String passenger_name = null;
        String passenger_email = null;

        BookingDtos.BookingResponse bookingResponse = new BookingDtos.BookingResponse( id, createdAt, passenger_name, passenger_email, items );

        return bookingResponse;
    }

    @Override
    public BookingDtos.BookingItemResponse toItemResponse(BookingItem entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String cabin = null;
        BigDecimal price = null;
        Integer segmentOrder = null;

        id = entity.getId();
        if ( entity.getCabin() != null ) {
            cabin = entity.getCabin().name();
        }
        price = entity.getPrice();
        segmentOrder = entity.getSegmentOrder();

        Long booking_id = null;
        Long flight_id = null;
        String flight_number = null;

        BookingDtos.BookingItemResponse bookingItemResponse = new BookingDtos.BookingItemResponse( id, cabin, price, segmentOrder, booking_id, flight_id, flight_number );

        return bookingItemResponse;
    }

    @Override
    public List<BookingDtos.BookingItemResponse> toItemResponseList(List<BookingItem> entities) {
        if ( entities == null ) {
            return null;
        }

        List<BookingDtos.BookingItemResponse> list = new ArrayList<BookingDtos.BookingItemResponse>( entities.size() );
        for ( BookingItem bookingItem : entities ) {
            list.add( toItemResponse( bookingItem ) );
        }

        return list;
    }

    @Override
    public void itemPatch(BookingDtos.BookingItemUpdateRequest request, BookingItem entity) {
        if ( request == null ) {
            return;
        }

        if ( request.price() != null ) {
            entity.setPrice( request.price() );
        }
        if ( request.segmentOrder() != null ) {
            entity.setSegmentOrder( request.segmentOrder() );
        }
        if ( request.cabin() != null ) {
            entity.setCabin( mapCabin( request.cabin() ) );
        }
    }

    @Override
    public void updateBookingFromRequest(BookingDtos.BookingItemUpdateRequest request, Booking entity) {
        if ( request == null ) {
            return;
        }
    }
}
