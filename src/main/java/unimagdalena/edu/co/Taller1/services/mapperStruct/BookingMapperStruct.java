package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.domine.entities.*;
import unimagdalena.edu.co.Taller1.api.dto.BookingDtos.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapperStruct {

    @Mapping(source = "passenger.fullName", target = "passengerName")
    @Mapping(source = "passenger.email", target = "passengerEmail")
    @Mapping(source = "items", target = "items")
    BookingResponse toResponse(Booking entity);

    @Mapping(source = "booking.id", target = "bookingId")
    @Mapping(source = "flight.id", target = "flightId")
    @Mapping(source = "flight.number", target = "flightNumber")
    BookingItemResponse toItemResponse(BookingItem entity);

    List<BookingItemResponse> toItemResponseList(List<BookingItem> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateItemFromRequest(BookingItemUpdateRequest request, @MappingTarget BookingItem entity);

    default Cabin mapCabin(String cabin) {
        return cabin != null ? Cabin.valueOf(cabin.toUpperCase()) : null;
    }

}
