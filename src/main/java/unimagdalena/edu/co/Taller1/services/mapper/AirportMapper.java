package unimagdalena.edu.co.Taller1.services.mapper;

import unimagdalena.edu.co.Taller1.api.dto.AirportDtos.*;
import unimagdalena.edu.co.Taller1.entities.Airport;

public class AirportMapper {
    public static Airport toEntity(AirportCreateRequest dto) {
        if (dto == null) return null;
        Airport entity = new Airport();
        entity.setCode(dto.code());
        entity.setName(dto.name());
        entity.setCity(dto.city());
        return entity;
    }

    public static void toUpdateEntity(AirportUpdateRequest dto, Airport entity) {
        if (dto == null || entity == null) return;
        entity.setCode(dto.code());
        entity.setName(dto.name());
        entity.setCity(dto.city());
    }

    public static AirportResponse toResponse(Airport entity) {
        if (entity == null) return null;
        return new AirportResponse(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getCity()
        );
    }
}
