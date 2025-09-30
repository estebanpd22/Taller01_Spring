package unimagdalena.edu.co.Taller1.services.mapper;

import unimagdalena.edu.co.Taller1.api.dto.AirportDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Airport;

public class AirportMapper {
    public static Airport toEntity(AirportCreateRequest request){
        return Airport.builder().code(request.code()).name(request.name()).city(request.city()).build();
    }

    public static AirportResponse toResponse(Airport entity){
        return new AirportResponse(entity.getId(), entity.getCode(),  entity.getName(), entity.getCity());
    }

    public static void patch(Airport entity, AirportUpdateRequest request){
        if (request.code() != null) entity.setCode(request.code());
        if (request.name() != null) entity.setName(request.name());
    }
}
