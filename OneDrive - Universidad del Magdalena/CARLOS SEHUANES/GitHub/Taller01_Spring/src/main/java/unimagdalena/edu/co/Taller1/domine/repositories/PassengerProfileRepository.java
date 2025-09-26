package unimagdalena.edu.co.Taller1.domine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unimagdalena.edu.co.Taller1.domine.entities.PassengerProfile;

public interface PassengerProfileRepository extends JpaRepository<PassengerProfile,Long> {
}