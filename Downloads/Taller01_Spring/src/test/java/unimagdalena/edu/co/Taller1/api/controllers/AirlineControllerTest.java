package unimagdalena.edu.co.Taller1.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import unimagdalena.edu.co.Taller1.api.dto.AirlineDtos.*;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.AirlineService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AirlineControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @MockitoBean
    AirlineService service;

    @Test
    void createAirline_shouldReturn201AndLocation() throws Exception {
        var req = new AirlineCreateRequest("AA", "American Airlines");
        var resp = new AirlineResponse(1L, "AA", "American Airlines");

        when(service.create(any())).thenReturn(resp);

        mvc.perform(post("/api/v1/airlines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/api/v1/airlines/1")))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.code").value("AA"))
                .andExpect(jsonPath("$.name").value("American Airlines"));
    }

    @Test
    void createAirline_shouldReturn400WhenCodeInvalid() throws Exception {
        var req = new AirlineCreateRequest("A", "American Airlines");

        mvc.perform(post("/api/v1/airlines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAirline_shouldReturn200() throws Exception {
        when(service.getById(1L)).thenReturn(new AirlineResponse(1L, "AA", "American Airlines"));

        mvc.perform(get("/api/v1/airlines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.code").value("AA"));
    }

    @Test
    void getAirline_shouldReturn404WhenNotFound() throws Exception {
        when(service.getById(99L)).thenThrow(new NotFoundException("Airline 99 not found"));

        mvc.perform(get("/api/v1/airlines/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Airline 99 not found"));
    }

    @Test
    void getAirlineByCode_shouldReturn200() throws Exception {
        when(service.getByCode("AA")).thenReturn(new AirlineResponse(1L, "AA", "American Airlines"));

        mvc.perform(get("/api/v1/airlines")
                        .param("code", "AA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("AA"));
    }

    @Test
    void updateAirline_shouldReturn200() throws Exception {
        var req = new AirlineUpdateRequest("AA", "American Airlines Updated");
        var resp = new AirlineResponse(1L, "AA", "American Airlines Updated");

        when(service.update(eq(1L), any())).thenReturn(resp);

        mvc.perform(patch("/api/v1/airlines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("American Airlines Updated"));
    }

    @Test
    void deleteAirline_shouldReturn204() throws Exception {
        mvc.perform(delete("/api/v1/airlines/1"))
                .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }

    @Test
    void listAllAirlines_shouldReturn200WithList() throws Exception {
        List<AirlineResponse> airlines = Arrays.asList(
                new AirlineResponse(1L, "AA", "American Airlines"),
                new AirlineResponse(2L, "DL", "Delta Airlines")
        );
        when(service.airlineList()).thenReturn(airlines);

        mvc.perform(get("/api/v1/airlines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].code").value("AA"))
                .andExpect(jsonPath("$[1].code").value("DL"));
    }
}
