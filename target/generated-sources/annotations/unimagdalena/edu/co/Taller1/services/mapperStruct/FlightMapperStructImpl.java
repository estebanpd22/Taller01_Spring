package unimagdalena.edu.co.Taller1.services.mapperStruct;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos;
import unimagdalena.edu.co.Taller1.domine.entities.Airline;
import unimagdalena.edu.co.Taller1.domine.entities.Airport;
import unimagdalena.edu.co.Taller1.domine.entities.Flight;
import unimagdalena.edu.co.Taller1.domine.entities.Tag;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-03T16:09:47-0500",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class FlightMapperStructImpl implements FlightMapperStruct {

    @Override
    public Flight toEntity(FlightDtos.FlightCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Flight.FlightBuilder flight = Flight.builder();

        flight.number( request.number() );
        flight.departureTime( request.departureTime() );
        flight.arrivalTime( request.arrivalTime() );

        return flight.build();
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

        id = flight.getId();
        number = flight.getNumber();
        departureTime = flight.getDepartureTime();
        arrivalTime = flight.getArrivalTime();
        airline = airlineToAirlineRef( flight.getAirline() );
        origin = airportToAirportRef( flight.getOrigin() );
        destination = airportToAirportRef( flight.getDestination() );
        tags = tagSetToTagRefSet( flight.getTags() );

        FlightDtos.FlightResponse flightResponse = new FlightDtos.FlightResponse( id, number, departureTime, arrivalTime, airline, origin, destination, tags );

        return flightResponse;
    }

    @Override
    public void patch(Flight entity, FlightDtos.FlightUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        if ( request.number() != null ) {
            entity.setNumber( request.number() );
        }
        if ( request.departureTime() != null ) {
            entity.setDepartureTime( request.departureTime() );
        }
        if ( request.arrivalTime() != null ) {
            entity.setArrivalTime( request.arrivalTime() );
        }
    }

    protected FlightDtos.AirlineRef airlineToAirlineRef(Airline airline) {
        if ( airline == null ) {
            return null;
        }

        Long id = null;
        String code = null;
        String name = null;

        id = airline.getId();
        code = airline.getCode();
        name = airline.getName();

        FlightDtos.AirlineRef airlineRef = new FlightDtos.AirlineRef( id, code, name );

        return airlineRef;
    }

    protected FlightDtos.AirportRef airportToAirportRef(Airport airport) {
        if ( airport == null ) {
            return null;
        }

        Long id = null;
        String code = null;
        String city = null;

        id = airport.getId();
        code = airport.getCode();
        city = airport.getCity();

        FlightDtos.AirportRef airportRef = new FlightDtos.AirportRef( id, code, city );

        return airportRef;
    }

    protected FlightDtos.TagRef tagToTagRef(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = tag.getId();
        name = tag.getName();

        FlightDtos.TagRef tagRef = new FlightDtos.TagRef( id, name );

        return tagRef;
    }

    protected Set<FlightDtos.TagRef> tagSetToTagRefSet(Set<Tag> set) {
        if ( set == null ) {
            return null;
        }

        Set<FlightDtos.TagRef> set1 = new LinkedHashSet<FlightDtos.TagRef>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Tag tag : set ) {
            set1.add( tagToTagRef( tag ) );
        }

        return set1;
    }
}
