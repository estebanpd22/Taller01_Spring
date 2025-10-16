package unimagdalena.edu.co.Taller1.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import unimagdalena.edu.co.Taller1.api.dto.BookingDtos.*;
import unimagdalena.edu.co.Taller1.api.dto.BookingItemDtos.*;
import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos.*;
import unimagdalena.edu.co.Taller1.services.BookingService;

import java.time.OffsetDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private BookingService bookingService;
    @Test
    void create_shouldReturn201() throws Exception {

        var request = new BookingCreateRequest(OffsetDateTime.now(),1L, List.of());
        var passenger = new PassengerResponse(11L,"Joselito", "jose@gmail.com",new PassengerProfileDto("33333", "1"));
        var response = new BookingResponse(55L,OffsetDateTime.now(),passenger,List.of());

        when(bookingService.createBooking(any(BookingCreateRequest.class))).thenReturn(response);
        mockMvc.perform(post("/api/bookings").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",org.hamcrest.Matchers.endsWith("/api/bookings/55")))
                .andExpect(jsonPath("$.id").value(55));

    }
    @Test
    void get_shouldReturn200() throws Exception {
        var request = new BookingCreateRequest(OffsetDateTime.now(),1L, List.of());
        var passenger = new PassengerResponse(11L,"Joselito", "jose@gmail.com",new PassengerProfileDto("33333", "1"));
        var response = new BookingResponse(55L,OffsetDateTime.now(),passenger,List.of());
        when(bookingService.getBookingId(eq(55L))).thenReturn(response);
        mockMvc.perform(get("/api/bookings/55"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(55));
    }
    @Test
    void findByPassenger_shouldReturn200() throws Exception {
        var request = new BookingCreateRequest(OffsetDateTime.now(),11L, List.of());
        var passenger = new PassengerResponse(11L,"Joselito", "jose@gmail.com",new PassengerProfileDto("33333", "1"));
        var response = new BookingResponse(55L,OffsetDateTime.now(),passenger,List.of());
        when(bookingService.finBookingByPassengerEmail("jose@gmail.com")).thenReturn(List.of(response));
        mockMvc.perform(get("/api/bookings/by-passenger")
                        .param("email","jose@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(55));
    }
    @Test
    void getBookingWithDetails_shouldReturn200() throws Exception {
        var request = new BookingCreateRequest(OffsetDateTime.now(),11L, List.of());
        var passenger = new PassengerResponse(11L,"Joselito", "jose@gmail.com",new PassengerProfileDto("33333", "1"));
        var response = new BookingResponse(55L,OffsetDateTime.now(),passenger,List.of());
        when(bookingService.getBookingWithDetails(eq(55L))).thenReturn(response);
        mockMvc.perform(get("/api/bookings/booking-with-details/55"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(55));
    }

    @Test
    void update_shouldReturn200() throws Exception {
        var request = new BookingCreateRequest(OffsetDateTime.now(),1L, List.of());
        var passenger = new PassengerResponse(11L,"Joselito", "jose@gmail.com",new PassengerProfileDto("33333", "1"));
        var response = new BookingResponse(55L,OffsetDateTime.now(),passenger,List.of());
        when(bookingService.updateBooking(eq(55L),any(BookingUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(patch("/api/bookings/55")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(55));
    }
    @Test
    void delete_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/bookings/55"))
                .andExpect(status().isNoContent());
        verify(bookingService).deleteById(55L);
    }
}
