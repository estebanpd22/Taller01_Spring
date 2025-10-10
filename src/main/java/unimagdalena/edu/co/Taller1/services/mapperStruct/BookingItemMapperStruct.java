package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import unimagdalena.edu.co.Taller1.api.dto.BookingItemDtos;
import unimagdalena.edu.co.Taller1.domine.entities.Booking;
import unimagdalena.edu.co.Taller1.domine.entities.BookingItem;
import unimagdalena.edu.co.Taller1.domine.entities.BookingItem.*;

import java.util.List;

public interface BookingItemMapperStruct {
    BookingItem toEntity(BookingItemDtos.BookingItemCreateRequest request);

    BookingItemDtos.BookingItemResponse toItemResponse(BookingItem entity);

    List<BookingItemDtos.BookingItemResponse> toItemResponseList(List<BookingItem> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void itemPatch(BookingItemDtos.BookingItemUpdateRequest request, @MappingTarget BookingItem entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookingFromRequest(BookingItemDtos.BookingItemUpdateRequest request, @MappingTarget Booking entity);
}

