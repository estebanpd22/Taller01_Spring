package unimagdalena.edu.co.Taller1.services.mapperStruct;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import unimagdalena.edu.co.Taller1.api.dto.AirportDtos;
import unimagdalena.edu.co.Taller1.domine.entities.Airport;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-03T14:00:05-0500",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class AirportMapperStructImpl implements AirportMapperStruct {

    @Override
    public Airport toEntity(AirportDtos.AirportCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Airport.AirportBuilder airport = Airport.builder();

        airport.code( request.code() );
        airport.name( request.name() );
        airport.city( request.city() );

        return airport.build();
    }

    @Override
    public AirportDtos.AirportResponse toResponse(Airport entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String code = null;
        String name = null;
        String city = null;

        id = entity.getId();
        code = entity.getCode();
        name = entity.getName();
        city = entity.getCity();

        AirportDtos.AirportResponse airportResponse = new AirportDtos.AirportResponse( id, code, name, city );

        return airportResponse;
    }

    @Override
    public void patch(Airport entity, AirportDtos.AirportUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        if ( request.code() != null ) {
            entity.setCode( request.code() );
        }
        if ( request.name() != null ) {
            entity.setName( request.name() );
        }
    }
}
