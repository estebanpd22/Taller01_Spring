package unimagdalena.edu.co.Taller1.services.mapperStruct;

import java.time.OffsetDateTime;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos;
import unimagdalena.edu.co.Taller1.domine.entities.Flight;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-03T11:21:28-0500",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class FlightMapperStructImpl implements FlightMapperStruct {

    @Override
    public Flight toEntity(FlightDtos.FlightCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Flight flight = new Flight();

        return flight;
    }

    @Override
    public FlightDtos.FlightResponse toResponse(Flight flight) {
        if ( flight == null ) {
            return null;
        }

        Long id = null;
        String number = null;
        OffsetDateTime departureTime = null;
        OffsetDateTime arrivalTime = null;
        FlightDtos.AirlineRef airline = null;
        FlightDtos.AirportRef origin = null;
        FlightDtos.AirportRef destination = null;
        Set<FlightDtos.TagRef> tags = null;

        FlightDtos.FlightResponse flightResponse = new FlightDtos.FlightResponse( id, number, departureTime, arrivalTime, airline, origin, destination, tags );

        return flightResponse;
    }

    @Override
    public void patch(Flight entity, FlightDtos.FlightUpdateRequest request) {
        if ( request == null ) {
            return;
        }
    }
}
