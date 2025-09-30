package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.AirlineDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineService {
    AirlineResponse createAirline(AirlineCreateRequest request);
    AirlineResponse getAirline(Long id);
    AirlineResponse getAirlineByCode(String code);
    AirlineResponse updateAirline(Long id, AirlineUpdateRequest request);
    void deleteAirline(Long id);
    List<AirlineResponse> listAllAirlines();
    Page<AirlineResponse> listAllAirlinesPage(Pageable pageable);
}