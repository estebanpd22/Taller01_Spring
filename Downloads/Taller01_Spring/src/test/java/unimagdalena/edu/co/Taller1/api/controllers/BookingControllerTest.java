package unimagdalena.edu.co.Taller1.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import unimagdalena.edu.co.Taller1.api.dto.BookingDtos.*;
import unimagdalena.edu.co.Taller1.api.dto.BookingItemDtos.*;
import unimagdalena.edu.co.Taller1.exceptions.NotFoundException;
import unimagdalena.edu.co.Taller1.services.BookingService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookingControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @MockitoBean
    BookingService service;

    @Test
    void createBooking_shouldReturn201AndLocation() throws Exception {
        var req = new BookingCreateRequest(1L);
        var item = new BookingItemResponse(1L, "Economy", new BigDecimal("250.00"), 1, 1L, 101L, "AA123");
        var resp = new BookingResponse(1L, OffsetDateTime.now(), "John Doe", "john@example.com",
                Collections.singletonList(item));

        when(service.create(any())).thenReturn(resp);

        mvc.perform(post("/api/v1/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/api/v1/bookings/1")))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.passenger_name").value("John Doe"));
    }

    @Test
    void createBooking_shouldReturn400WhenPassengerIdNull() throws Exception {
        var req = new BookingCreateRequest(null);

        mvc.perform(post("/api/v1/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getBooking_shouldReturn200() throws Exception {
        var resp = new BookingResponse(1L, OffsetDateTime.now(), "John Doe", "john@example.com",
                Collections.emptyList());
        when(service.getById(1L)).thenReturn(resp);

        mvc.perform(get("/api/v1/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.passenger_name").value("John Doe"));
    }

    @Test
    void getBooking_shouldReturn404WhenNotFound() throws Exception {
        when(service.getById(99L)).thenThrow(new NotFoundException("Booking 99 not found"));

        mvc.perform(get("/api/v1/bookings/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Booking 99 not found"));
    }


    @Test
    void listBookingsByPassengerEmail_shouldReturn200WithPage() throws Exception {
        var resp = new BookingResponse(1L, OffsetDateTime.now(), "John Doe", "john@example.com",
                Collections.emptyList());
        Page<BookingResponse> page = new PageImpl<>(List.of(resp));

        when(service.listBookingsByPassengerEmailAndOrderedMostRecently(anyString(), any(Pageable.class)))
                .thenReturn(page);

        mvc.perform(get("/api/v1/bookings")
                        .param("email", "john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }


    @Test
    void updateBooking_shouldReturn200() throws Exception {
        var resp = new BookingResponse(1L, OffsetDateTime.now(), "Jane Doe", "jane@example.com",
                Collections.emptyList());

        when(service.update(eq(1L), any(BookingUpdateRequest.class))).thenReturn(resp);

        mvc.perform(patch("/api/v1/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deleteBooking_shouldReturn204() throws Exception {
        mvc.perform(delete("/api/v1/bookings/1"))
                .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }
}
