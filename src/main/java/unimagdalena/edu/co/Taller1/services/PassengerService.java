package unimagdalena.edu.co.Taller1.services;

import unimagdalena.edu.co.Taller1.api.dto.PassengerDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PassengerService {
    PassengerResponse createPassenger(PassengerCreateRequest passengerCreateRequest);
    PassengerResponse updatePassenger(Long id,PassengerUpdateRequest passengerUpdateRequest);
    PassengerResponse get(Long id);
    PassengerResponse getByEmail(String email);
    PassengerResponse getByEmailWithProfile(String email);
    void delete(Long id);
}