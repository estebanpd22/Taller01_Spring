package unimagdalena.edu.co.Taller1.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import unimagdalena.edu.co.Taller1.api.dto.AirportDtos.*;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.AirportService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AirportController.class)
public class AirportControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @MockitoBean
    AirportService service;

    @Test
    void createAirport_shouldReturn201AndLocation() throws Exception {
        var req = new AirportCreateRequest("JFK", "John F Kennedy", "New York");
        var resp = new AirportResponse(1L, "JFK", "John F Kennedy", "New York");

        when(service.create(any())).thenReturn(resp);

        mvc.perform(post("/api/airports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/api/airports/1")))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.code").value("JFK"))
                .andExpect(jsonPath("$.city").value("New York"));
    }

    @Test
    void createAirport_shouldReturn400WhenCodeInvalid() throws Exception {
        var req = new AirportCreateRequest("J", "JFK", "New York");

        mvc.perform(post("/api/airports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAirport_shouldReturn200() throws Exception {
        when(service.get(1L)).thenReturn(new AirportResponse(1L, "JFK", "John F Kennedy", "New York"));

        mvc.perform(get("/api/airports/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.code").value("JFK"));
    }

    @Test
    void getAirport_shouldReturn404WhenNotFound() throws Exception {
        when(service.get(99L)).thenThrow(new NotFoundException("Airport 99 not found"));

        mvc.perform(get("/api/airports/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Airport 99 not found"));
    }

    @Test
    void getAirportByCode_shouldReturn200() throws Exception {
        when(service.getByCode("JFK")).thenReturn(new AirportResponse(1L, "JFK", "John F Kennedy", "New York"));

        mvc.perform(get("/api/airports/by-code")
                        .param("code", "JFK"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("JFK"));
    }

    @Test
    void getCityAirports_shouldReturn200WithList() throws Exception {
        List<AirportResponse> airports = Arrays.asList(
                new AirportResponse(1L, "JFK", "John F Kennedy", "New York"),
                new AirportResponse(2L, "LGA", "LaGuardia", "New York")
        );
        when(service.cityList("New York")).thenReturn(airports);

        mvc.perform(get("/api/airports/by-city")
                        .param("city", "New York"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].city").value("New York"));
    }

    @Test
    void updateAirport_shouldReturn200() throws Exception {
        var req = new AirportUpdateRequest("JFK", "JFK International", "New York");
        var resp = new AirportResponse(1L, "JFK", "JFK International", "New York");

        when(service.update(eq(1L), any())).thenReturn(resp);

        mvc.perform(patch("/api/airports/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("JFK International"));
    }

    @Test
    void deleteAirport_shouldReturn204() throws Exception {
        mvc.perform(delete("/api/airports/1"))
                .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }

    @Test
    void listAllAirports_shouldReturn200WithPage() throws Exception {
        Page<AirportResponse> page = new PageImpl<>(List.of(
                new AirportResponse(1L, "JFK", "John F Kennedy", "New York")
        ));

        when(service.airportList(any(PageRequest.class))).thenReturn(page);

        mvc.perform(get("/api/airports")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}
