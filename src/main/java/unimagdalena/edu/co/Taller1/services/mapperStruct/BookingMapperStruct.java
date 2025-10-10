package unimagdalena.edu.co.Taller1.services.mapperStruct;

import org.mapstruct.*;
import unimagdalena.edu.co.Taller1.domine.entities.*;
import unimagdalena.edu.co.Taller1.api.dto.BookingDtos.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookingMapperStruct {

    BookingResponse toResponse(Booking entity);

    default Cabin mapCabin(String cabin) {
        return cabin != null ? Cabin.valueOf(cabin.toUpperCase()) : null;
    }

    void addItem(BookingItem item, Booking booking);
}

