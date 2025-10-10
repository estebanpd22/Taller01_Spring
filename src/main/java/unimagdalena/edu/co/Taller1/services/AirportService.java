package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.AirportDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirportService {
    AirportResponse create(AirportCreateRequest request);
    AirportResponse getById(Long id);
    AirportResponse getByCode(String code);
    List<AirportResponse> getCityList(String city);
    AirportResponse update(Long id, AirportUpdateRequest request);
    void delete(Long id);
    Page<AirportResponse> airportList(Pageable pageable);
}
