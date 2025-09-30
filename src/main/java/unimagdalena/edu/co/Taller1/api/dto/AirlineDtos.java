package unimagdalena.edu.co.Taller1.api.dto;

import java.io.Serializable;

public class AirlineDtos {
    public record AirlineCreateRequest(String code, String name) implements Serializable {}

    public record AirlineUpdateRequest(String id, String code, String name) implements Serializable {}

    public record AirlineResponse(Long id, String code, String name) implements Serializable {}

}
