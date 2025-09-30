package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PassengerService {
    PassengerResponse createPassenger(PassengerCreateRequest request);
    PassengerResponse getPassenger(Long id);
    PassengerResponse getPassengerByEmail(String email);
    PassengerResponse getPassengerWithProfile(String email);
    PassengerResponse updatePassenger(Long id, PassengerUpdateRequest request);
    void deletePassenger(Long id);
    Page<PassengerResponse> listAllPassengers(Pageable pageable);
}