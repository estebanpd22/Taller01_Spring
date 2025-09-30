package unimagdalena.edu.co.Taller1.api.dto;

import java.io.Serializable;

public class TagDtos {
    public record TagCreateRequest( String name) implements Serializable {}
    public record TagUpdateRequest(Long id,String name) implements Serializable {}
    public record TagResponse(Long id, String name) implements Serializable {}
}