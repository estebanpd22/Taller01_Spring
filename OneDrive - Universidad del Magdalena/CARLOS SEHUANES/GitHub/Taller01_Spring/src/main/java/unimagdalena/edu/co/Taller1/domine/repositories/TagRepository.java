package unimagdalena.edu.co.Taller1.domine.repositories;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import unimagdalena.edu.co.Taller1.domine.entities.Tag;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {
    // Busca una etiqueta por su nombre
    Optional<Tag> findByNameIgnoreCase(String name);

    // Retorna todas las etiquetas cuyos nombres estén en la lista dada
    List<Tag> findByNameIgnoreCaseIn(Collection<String> names, Limit limit);
}