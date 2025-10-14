/*
package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.api.dto.BookingDtos;
import unimagdalena.edu.co.Taller1.entities.Booking;
import unimagdalena.edu.co.Taller1.entities.BookingItem;
import unimagdalena.edu.co.Taller1.entities.Passenger;
import unimagdalena.edu.co.Taller1.services.mapper.BookingMapper;
import unimagdalena.edu.co.Taller1.services.mapper.PassengerMapper;

import java.util.List;

@Mapper(
        componentModel = "spring", // Genera un @Component de Spring
        uses = {PassengerMapper.class, BookingMapper.class}, // Usa otros mappers
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookingMapperStruct {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passenger", source = "passenger")
    @Mapping(target = "items", source = "items")
    @Mapping(target = "createdAt", source = "request.createdAt")
    Booking toEntity(
            BookingDtos.BookingCreateRequest request,
            Passenger passenger,
            List<BookingItem> items
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "passenger", source = "passenger")
    void updateEntity(@MappingTarget Booking booking, Passenger passenger);

    @Mapping(target = "passengerId", source = "passenger.id")
    @Mapping(target = "passengerResponse", source = "passenger")
    @Mapping(target = "items", source = "items")
    BookingDtos.BookingResponse toResponse(Booking booking);

}
*/