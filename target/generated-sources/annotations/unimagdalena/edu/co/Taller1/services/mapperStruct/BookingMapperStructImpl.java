package unimagdalena.edu.co.Taller1.services.mapperStruct;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import unimagdalena.edu.co.Taller1.api.dto.BookingDtos.*;
import unimagdalena.edu.co.Taller1.api.dto.BookingItemDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Booking;
import unimagdalena.edu.co.Taller1.domine.entities.BookingItem;


@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-03T16:09:47-0500",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class BookingMapperStructImpl implements BookingMapperStruct {

    @Override
    public BookingResponse toResponse(Booking entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        OffsetDateTime createdAt = null;
        List<BookingItemResponse> items = null;

        id = entity.getId();
        createdAt = entity.getCreatedAt();

        String passenger_name = null;
        String passenger_email = null;

        BookingResponse bookingResponse = new BookingResponse( id, createdAt, passenger_name, passenger_email, items );

        return bookingResponse;
    }



    @Override
    public void addItem(BookingItem item, Booking booking) {
        if(item != null &&  booking != null) {
            booking.getItems().add(item);
            item.setBooking(booking);
        }
    }
}
