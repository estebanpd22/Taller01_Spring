package unimagdalena.edu.co.Taller1.api.dto;

import java.io.Serializable;
import java.util.Collection;

public class TagDtos {
    public record TagCreateRequest( String name) implements Serializable {}
    public record TagUpdateRequest(String name) implements Serializable {}
    public record TagResponse(Long id, String name, Collection<FlightDtos.FlightResponse> flights) implements Serializable {}
}