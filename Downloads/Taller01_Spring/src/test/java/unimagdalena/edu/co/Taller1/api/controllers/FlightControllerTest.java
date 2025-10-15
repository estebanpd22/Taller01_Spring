package unimagdalena.edu.co.Taller1.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos;
import unimagdalena.edu.co.Taller1.api.dto.FlightDtos.*;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.FlightService;

import java.time.OffsetDateTime;
import java.util.Set;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FlightControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    FlightService flightService;

    @Test
    void create_ShouldReturn201AndLocation() throws Exception {
        var departureTime = OffsetDateTime.now();
        var arrivalTime = OffsetDateTime.now().plusHours(2);
        var request = new FlightDtos.FlightCreateRequest("AV123", departureTime, arrivalTime, 1L, 1L, 2L, Set.of(1L));
        var airline = new FlightDtos.AirlineRef(1L, "AV", "Avianca");
        var origin = new FlightDtos.AirportRef(1L, "BOG", "Bogota");
        var destination = new FlightDtos.AirportRef(2L, "MDE", "Medellin");
        var tag = new FlightDtos.TagRef(1L, "Direct");
        var response = new FlightDtos.FlightResponse(1L, "AV123", departureTime, arrivalTime, airline, origin, destination, Set.of(tag));

        when(flightService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/flights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",endsWith("/api/flights/1")))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.number").value("AV123"));
    }

    @Test
    void get_ShouldReturn404() throws Exception {
        when(flightService.getById(99L)).thenThrow(new NotFoundException("Flight 99 not found"));

        mockMvc.perform(get("/api/v1/flights/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Flight 99 not found"));
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/v1/flights/8"))
                .andExpect(status().isNoContent());
        verify(flightService).delete(8L);
    }

    @Test
    void getByAirline_ShouldReturn200() throws Exception {
        var departureTime = OffsetDateTime.now();
        var arrivalTime = OffsetDateTime.now().plusHours(2);
        var airline = new FlightDtos.AirlineRef(1L, "AV", "Avianca");
        var origin = new FlightDtos.AirportRef(1L, "BOG", "Bogota");
        var destination = new FlightDtos.AirportRef(2L, "MDE", "Medellin");
        var tag = new FlightDtos.TagRef(1L, "Direct");
        var response = new FlightDtos.FlightResponse(5L, "AV123", departureTime, arrivalTime, airline, origin, destination, Set.of(tag));

        when(flightService.getById(5L)).thenReturn(response);

        mockMvc.perform(get("/api/flights/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.number").value("AV123"));
    }
}
