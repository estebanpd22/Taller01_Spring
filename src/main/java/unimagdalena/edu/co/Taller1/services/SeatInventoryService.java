package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.SeatInventoryDtos.*;
import unimagdalena.edu.co.Taller1.entities.Cabin;

import java.util.List;

public interface SeatInventoryService {
    SeatInventoryResponse create(SeatInventoryCreateRequest request);
    SeatInventoryResponse getById(Long id);
    List<SeatInventoryResponse> getAll();
    SeatInventoryResponse update(Long id, SeatInventoryUpdateRequest request);
    void delete(Long id);

    SeatInventoryResponse findByFlightAndCabin(Long flightId, Cabin cabin);
    boolean hasAvailableSeats(Long flightId, Cabin cabin, Integer minSeats);
    boolean decrementAvailableSeats(Long flightId, Cabin cabin, int quantity);
    boolean incrementAvailableSeats(Long flightId, Cabin cabin, int quantity);
}