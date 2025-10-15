package unimagdalena.edu.co.Taller1.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import unimagdalena.edu.co.Taller1.api.dto.SeatInventoryDtos.*;
import unimagdalena.edu.co.Taller1.domine.entities.Cabin;
import unimagdalena.edu.co.Taller1.services.SeatInventoryService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class SeatInventoryControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;
    @MockitoBean
    SeatInventoryService seatService;

    @Test
    void create_ShouldReturn201() throws Exception {
        var request = new SeatInventoryCreateRequest(Cabin.ECONOMY, 50, 25, 1001L);
        var response = new SeatInventoryResponse(100001L, "ECONOMY", 50, 25, 1001L);

        when(seatService.create(eq(1001L), any())).thenReturn(response);

        mvc.perform(post("/api/v1/flights/1001/seatInventories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",
                        org.hamcrest.Matchers.endsWith("/api/v1/flights/1001/seatInventories/100001")))
                .andExpect(jsonPath("$.id").value(100001));

    }

    @Test
    void delete_shouldReturn204() throws Exception{
        mvc.perform(delete("/api/v1/flights/1001/seatInventories/5"))
                .andExpect(status().isNoContent());
        verify(seatService).delete(5L);
    }

    @Test
    void listByFlight_ShouldReturn200() throws Exception{
        when(seatService.listSeatInventoriesByFlight(eq(1001L))).thenReturn(List.of(
                new SeatInventoryResponse(100001L, "ECONOMY", 50, 25, 1001L),
                new SeatInventoryResponse(100002L, "PREMIUM", 50, 1, 1001L)
        ));

        mvc.perform(get("/api/v1/flights/1001/seatInventories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(100001))
                .andExpect(jsonPath("$[1].id").value(100002));
    }
}
