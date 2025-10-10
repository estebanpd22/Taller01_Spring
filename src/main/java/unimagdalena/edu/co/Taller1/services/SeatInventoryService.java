package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.SeatInventoryDtos.*;

import java.util.List;

public interface SeatInventoryService {
    //Basic CRUD
    SeatInventoryResponse create(Long flight_id, SeatInventoryCreateRequest request);
    SeatInventoryResponse getById(Long id);
    SeatInventoryResponse update(Long id, SeatInventoryUpdateRequest request);
    void delete(Long id);
    //--------------------------------------------//

    List<SeatInventoryResponse> listSeatInventoriesByFlight(Long flight_id);
    SeatInventoryResponse getSeatInventoryByFlightAndCabin(Long flight_id, String cabin);
    boolean existsSeatInventoryByFlightAndCabinWithMinAvailableSeats(Long flight_id, String cabin, Integer min);
}