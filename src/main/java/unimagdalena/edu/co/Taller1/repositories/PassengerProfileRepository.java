package unimagdalena.edu.co.Taller1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unimagdalena.edu.co.Taller1.entities.PassengerProfile;

public interface PassengerProfileRepository extends JpaRepository<PassengerProfile,Long> {
}

