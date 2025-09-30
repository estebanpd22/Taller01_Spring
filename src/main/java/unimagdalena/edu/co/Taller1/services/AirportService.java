package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.AirportDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirportService {
    AirportResponse createAirport(AirportCreateRequest request);
    AirportResponse getAirport(Long id);
    AirportResponse getAirportByCode(String code);
    List<AirportResponse> getCityAirports(String city);
    AirportResponse updateAirport(Long id, AirportUpdateRequest request);
    void deleteAirport(Long id);
    Page<AirportResponse> listAllAirports(Pageable pageable);
}
