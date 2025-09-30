package unimagdalena.edu.co.Taller1.domine.services.mapper;


import unimagdalena.edu.co.Taller1.api.dto.AirlineDtos;
import unimagdalena.edu.co.Taller1.domine.entities.Airline;

public class AirlineMapper {
    public Airline toEntity(AirlineDtos.AirlineCreateRequest dto) {
        if(dto == null) return null;
        Airline airline = new Airline();
        airline.setCode(dto.code());
        airline.setName(dto.name());
        return airline;
    }
}

