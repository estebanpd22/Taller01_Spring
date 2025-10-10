package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.domine.entities.*;
import unimagdalena.edu.co.Taller1.api.dto.BookingDtos.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapperStruct {

    BookingResponse toResponse(Booking entity);

    BookingItemResponse toItemResponse(BookingItem entity);

    List<BookingItemResponse> toItemResponseList(List<BookingItem> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void itemPatch(BookingItemUpdateRequest request, @MappingTarget BookingItem entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookingFromRequest(BookingItemUpdateRequest request, @MappingTarget Booking entity);

    default Cabin mapCabin(String cabin) {
        return cabin != null ? Cabin.valueOf(cabin.toUpperCase()) : null;
    }

    void addItem(BookingItem item, Booking booking);
}
