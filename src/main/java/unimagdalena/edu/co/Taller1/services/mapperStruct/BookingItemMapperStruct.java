/* package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.api.dto.BookingItemDtos.*;
import unimagdalena.edu.co.Taller1.entities.BookingItem;
import unimagdalena.edu.co.Taller1.entities.Flight;

@Mapper(
        componentModel = "spring",
        uses = {FlightMapperStruct.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookingItemMapperStruct {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "flight", source = "flight")
    BookingItem toEntity(BookingItemCreateRequest request, Flight flight);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "flight", source = "flight")
    void updateEntity(
            @MappingTarget BookingItem item,
            BookingItemUpdateRequest request,
            Flight flight
    );

    BookingItemResponse toResponse(BookingItem item);
}
*/
