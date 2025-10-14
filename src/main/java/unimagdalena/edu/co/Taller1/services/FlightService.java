package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.FlightDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;

public interface FlightService {

    FlightResponse create(FlightCreateRequest request);

    FlightResponse getById(Long id);

    List<FlightResponse> getAll();

    FlightResponse update(Long id, FlightUpdateRequest request);

    void delete(Long id);

    Page<FlightResponse> findByAirlineName(String airlineName, Pageable pageable);

    Page<FlightResponse> searchFlights(
            String originCode,
            String destinationCode,
            OffsetDateTime from,
            OffsetDateTime to,
            Pageable pageable
    );
}

