package unimagdalena.edu.co.Taller1.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos.*;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.FlightService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlightController.class)
public class FlightControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    FlightService service;

    @Test
    void create_shouldReturn201AndLocation() throws Exception {
        var departureTime = OffsetDateTime.now();
        var arrivalTime = OffsetDateTime.now().plusHours(2);
        var request = new FlightCreateRequest("AV123", departureTime, arrivalTime, 1L, 1L, 2L, Set.of(1L));
        var airline = new AirlineRef(1L, "AV", "Avianca");
        var origin = new AirportRef(1L, "BOG", "Bogota");
        var destination = new AirportRef(2L, "MDE", "Medellin");
        var tag = new TagRef(1L, "Direct");
        var response = new FlightResponse(1L, "AV123", departureTime, arrivalTime, airline, origin, destination, Set.of(tag));

        when(service.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",endsWith("/api/flights/1")))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.number").value("AV123"));
    }

    @Test
    void getById_shouldReturn200() throws Exception {
        var departureTime = OffsetDateTime.now();
        var arrivalTime = OffsetDateTime.now().plusHours(2);
        var airline = new AirlineRef(1L, "AV", "Avianca");
        var origin = new AirportRef(1L, "BOG", "Bogota");
        var destination = new AirportRef(2L, "MDE", "Medellin");
        var tag = new TagRef(1L, "Direct");
        var response = new FlightResponse(5L, "AV123", departureTime, arrivalTime, airline, origin, destination, Set.of(tag));

        when(service.getById(5L)).thenReturn(response);

        mockMvc.perform(get("/api/flights/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.number").value("AV123"));
    }

    @Test
    void getAll_shouldReturn200() throws Exception {
        var departureTime = OffsetDateTime.now();
        var arrivalTime = OffsetDateTime.now().plusHours(2);
        var airline = new AirlineRef(1L, "AV", "Avianca");
        var origin = new AirportRef(1L, "BOG", "Bogota");
        var destination = new AirportRef(2L, "MDE", "Medellin");
        var tag = new TagRef(1L, "Direct");
        var response1 = new FlightResponse(1L, "AV123", departureTime, arrivalTime, airline, origin, destination, Set.of(tag));
        var response2 = new FlightResponse(2L, "AV456", departureTime, arrivalTime, airline, origin, destination, Set.of(tag));

        when(service.getAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/api/flights"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void findByAirlineName_shouldReturn200() throws Exception {
        var departureTime = OffsetDateTime.now();
        var arrivalTime = OffsetDateTime.now().plusHours(2);
        var airline = new AirlineRef(1L, "AV", "Avianca");
        var origin = new AirportRef(1L, "BOG", "Bogota");
        var destination = new AirportRef(2L, "MDE", "Medellin");
        var tag = new TagRef(1L, "Direct");
        var response = new FlightResponse(1L, "AV123", departureTime, arrivalTime, airline, origin, destination, Set.of(tag));

        Page<FlightResponse> page = new PageImpl<>(List.of(response), PageRequest.of(0, 10, Sort.by("id").ascending()), 1);

        when(service.findByAirlineName(eq("Avianca"), any())).thenReturn(page);

        mockMvc.perform(get("/api/flights/by-airline")
                        .param("name", "Avianca")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].airline.name").value("Avianca"));
    }

    @Test
    void searchFlights_shouldReturn200() throws Exception {
        var departureTime = OffsetDateTime.now();
        var arrivalTime = OffsetDateTime.now().plusHours(2);
        var airline = new AirlineRef(1L, "AV", "Avianca");
        var origin = new AirportRef(1L, "BOG", "Bogota");
        var destination = new AirportRef(2L, "MDE", "Medellin");
        var tag = new TagRef(1L, "Direct");
        var response = new FlightResponse(1L, "AV123", departureTime, arrivalTime, airline, origin, destination, Set.of(tag));

        Page<FlightResponse> page = new PageImpl<>(List.of(response), PageRequest.of(0, 10, Sort.by("departureTime").ascending()), 1);

        when(service.searchFlights(eq("BOG"), eq("MDE"), any(), any(), any())).thenReturn(page);

        mockMvc.perform(get("/api/flights/search")
                        .param("origin", "BOG")
                        .param("destination", "MDE")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].origin.code").value("BOG"))
                .andExpect(jsonPath("$.content[0].destination.code").value("MDE"));
    }

    @Test
    void update_shouldReturn200() throws Exception {
        var departureTime = OffsetDateTime.now();
        var arrivalTime = OffsetDateTime.now().plusHours(2);
        var request = new FlightUpdateRequest("AV456", departureTime, arrivalTime, 1L, 1L, 2L, Set.of(1L));
        var airline = new AirlineRef(1L, "AV", "Avianca");
        var origin = new AirportRef(1L, "BOG", "Bogota");
        var destination = new AirportRef(2L, "MDE", "Medellin");
        var tag = new TagRef(1L, "Direct");
        var response = new FlightResponse(2L, "AV456", departureTime, arrivalTime, airline, origin, destination, Set.of(tag));

        when(service.update(eq(2L), any(FlightUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(patch("/api/flights/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.number").value("AV456"));
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/flights/2"))
                .andExpect(status().isNoContent());
        verify(service).delete(2L);
    }

    @Test
    void getById_shouldReturn404WhenNotFound() throws Exception {
        when(service.getById(5L)).thenThrow(new NotFoundException("Flight 5 Not Found"));

        mockMvc.perform(get("/api/flights/5"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Flight 5 Not Found"));
    }
}
