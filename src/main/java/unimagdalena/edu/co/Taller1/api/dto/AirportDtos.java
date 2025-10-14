package unimagdalena.edu.co.Taller1.api.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class AirportDtos {
    public record AirportCreateRequest(@NotBlank @Size(min= 2, max= 7) String code, @NotBlank String name, @NotBlank String city) implements Serializable {}
    public record AirportUpdateRequest(String code, String name, String city) implements Serializable {}
    public record AirportResponse(Long id,String code, String name, String city) implements Serializable {}
}

