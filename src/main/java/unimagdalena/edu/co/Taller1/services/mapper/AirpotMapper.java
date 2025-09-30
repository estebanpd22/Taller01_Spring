package unimagdalena.edu.co.Taller1.domine.services.mapper;

import unimagdalena.edu.co.Taller1.api.dto.AirportDtos;
import unimagdalena.edu.co.Taller1.domine.entities.Airport;

public class AirportMapper {
    public Airport toEntity(AirportDtos.AirportCreateRequest dto) {
        if (dto == null) return null;
        Airport entity = new Airport();
        entity.setCode(dto.code());
        entity.setName(dto.name());
        entity.setCity(dto.city());
        return entity;
    }

    public void toUpdateEntity(AirportDtos.AirportUpdateRequest dto, Airport entity) {
        if (dto == null || entity == null) return;
        entity.setCode(dto.code());
        entity.setName(dto.name());
        entity.setCity(dto.city());
    }

    public AirportDtos.AirportResponse toResponse(Airport entity) {
        if (entity == null) return null;
        return new AirportDtos.AirportResponse(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getCity()
        );
    }
}

