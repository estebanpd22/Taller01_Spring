package unimagdalena.edu.co.Taller1.services.mapperStruct;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import unimagdalena.edu.co.Taller1.api.dto.AirlineDtos;
import unimagdalena.edu.co.Taller1.domine.entities.Airline;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-02T23:32:08-0500",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class AirlineMapperStructImpl implements AirlineMapperStruct {

    @Override
    public Airline toEntity(AirlineDtos.AirlineCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Airline airline = new Airline();

        return airline;
    }

    @Override
    public AirlineDtos.AirlineResponse toResponse(Airline entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String code = null;
        String name = null;

        AirlineDtos.AirlineResponse airlineResponse = new AirlineDtos.AirlineResponse( id, code, name );

        return airlineResponse;
    }

    @Override
    public void patch(AirlineDtos.AirlineUpdateRequest request, Airline entity) {
        if ( request == null ) {
            return;
        }
    }
}
