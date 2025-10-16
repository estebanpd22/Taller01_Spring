package unimagdalena.edu.co.Taller1.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos.*;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.PassengerService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PassengerController.class)
public class PassengerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    PassengerService service;

    @Test
    void create_shouldReturn201AndLocation()throws Exception{
        var request = new PassengerCreateRequest("Joselito", "jose@gmail.com",
                new PassengerProfileDto("300554","57"));
        var response = new PassengerResponse(1L,"Joselito","jose@gmail.com",
                new PassengerProfileDto("300554","57"));

        when(service.createPassenger(any())).thenReturn(response);

        mockMvc.perform(post("/api/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",org.hamcrest.Matchers.endsWith("/api/passengers/1")))
                .andExpect(jsonPath("$.id").value(1));
    }
    @Test
    void get_shouldReturn200() throws Exception{
        when(service.get(5L)).thenReturn(new PassengerResponse(5L,"Jose R","jose@gmail.com",new PassengerProfileDto("300554","57")));

        mockMvc.perform(get("/api/passengers/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
    }
    @Test
    void getByEmail_shouldReturn200() throws Exception{
        when(service.getByEmail("jose@gmail.com")).thenReturn(new PassengerResponse(5L,"Jose R","jose@gmail.com",new PassengerProfileDto("300554","57")));

        mockMvc.perform(get("/api/passengers/email/jose@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("jose@gmail.com"));
    }
    @Test
    void getByEmailWithProfile_shouldReturn200() throws Exception{
        when(service.getByEmailWithProfile("jose@gmail.com")).thenReturn(new PassengerResponse(5L,"Jose R","jose@gmail.com",new PassengerProfileDto("300554","57")));

        mockMvc.perform(get("/api/passengers/email/jose@gmail.com/profile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("jose@gmail.com"));
    }
    @Test
    void update_shouldReturn200() throws Exception{
        var request = new PassengerCreateRequest("Joselito", "jose@gmail.com",
                new PassengerProfileDto("300554","57"));
        var response = new PassengerResponse(2L,"Joselito","jose@gmail.com",
                new PassengerProfileDto("300554","57"));
        when(service.updatePassenger(eq(2L),any(PassengerUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/passengers/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("jose@gmail.com"));
    }
    @Test
    void delete_shouldReturn204() throws Exception{
        mockMvc.perform(delete("/api/passengers/2"))
                .andExpect(status().isNoContent());
        verify(service).delete(2L);
    }
    @Test
    void get_shouldReturn404WhenNotFound() throws Exception{
        when(service.get(5L)).thenThrow(new NotFoundException("Passenger 5 Not Found"));

        mockMvc.perform(get("/api/passengers/5"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Passenger 5 Not Found"));
    }
}
