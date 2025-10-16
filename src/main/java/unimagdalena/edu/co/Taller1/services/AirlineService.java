package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.AirlineDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineService {
    AirlineResponse create(AirlineCreateRequest request);
    AirlineResponse get(Long id);

    AirlineResponse getByCode(String code);
    AirlineResponse update(Long id, AirlineUpdateRequest request);
    void delete(Long id);
    List<AirlineResponse> airlineList();
}