package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.FlightDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;

public interface FlightService {
    //Basic CRUD
    FlightResponse create(FlightCreateRequest request, Long airline_id, Long origin_airport_id, Long destination_airport_id);

    FlightResponse getById(Long id);

    FlightResponse update(FlightUpdateRequest request, Long id);

    void delete(Long id);

    //--------------------------------------------------------//
    //This method looks for flights by departureTime between two dates, and also, with an origin and destination (both can be optional).
    Page<FlightResponse> listScheduledFlights(Long origin_airport_id, Long destination_airport_id, OffsetDateTime from, OffsetDateTime to, Pageable pageable);

    FlightResponse addTagToFlight(Long flight_id, Long tag_id);

    FlightResponse removeTagFromFlight(Long flight_id, Long tag_id);

    Page<FlightResponse> listFlightsByAirline(Long airline_id, Pageable pageable);
}

