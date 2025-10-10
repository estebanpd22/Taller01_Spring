package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PassengerService {
    PassengerResponse create(PassengerCreateRequest request);
    PassengerResponse getById(Long id);
    PassengerResponse getByEmail(String email);
    PassengerResponse getPassengerWithProfile(String email);
    PassengerResponse update(Long id, PassengerUpdateRequest request);
    void delete(Long id);
    Page<PassengerResponse> listAllPassengers(Pageable pageable);
}