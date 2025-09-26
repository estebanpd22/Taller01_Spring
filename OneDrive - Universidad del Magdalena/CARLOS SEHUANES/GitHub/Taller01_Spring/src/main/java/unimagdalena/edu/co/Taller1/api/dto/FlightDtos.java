package unimagdalena.edu.co.Taller1.api.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;

public class FlightDtos {
    public record FlightCreateRequest(String number, OffsetDateTime departureTime, OffsetDateTime arrivalTime,
                                      Long airlineId, Long originId, Long destinationId, Set<Long> tagIds)implements Serializable {}
    public record FlightUpdateRequest(Long id, String number, OffsetDateTime departureTime, OffsetDateTime arrivalTime,
                                      Long airlineId,Long originId, Long destinationId, Set<Long> tagIds)implements Serializable {}
    public record FlightResponse(Long id, String number, OffsetDateTime departureTime, OffsetDateTime arrivalTime,
                                 AirlineDtos.AirlineResponse airline,
                                 AirportDtos.AirportResponse origin, AirportDtos.AirportResponse destination, Set<TagDtos.TagResponse> tags) implements Serializable {}
}
