package unimagdalena.edu.co.Taller1.api.dto;

import java.io.Serializable;

public class PassengerDtos {
    public record PassengerCreateRequest(String fullName, String email, PassengerProfileDto profileDto) implements Serializable {}
    public record PassengerUpdateRequest(String fullName, String email, PassengerProfileDto profileDto) implements Serializable {}
    public record PassengerProfileDto(String phone, String countryCode) implements Serializable {}
    public record PassengerResponse(Long id, String fullName, String email, PassengerProfileDto profileDto) implements Serializable {}
}
