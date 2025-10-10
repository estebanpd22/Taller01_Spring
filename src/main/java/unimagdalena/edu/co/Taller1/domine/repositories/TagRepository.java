package unimagdalena.edu.co.Taller1.domine.repositories;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import unimagdalena.edu.co.Taller1.domine.entities.Tag;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag,Long> {
    // Busca una etiqueta por su nombre
    Optional<Tag> findByNameIgnoreCase(String name);

    // Retorna todas las etiquetas cuyos nombres estén en la lista dada
    List<Tag> findByNameIn(Collection<String> names);

    Set<Tag> findTagsByIdIn(Collection<Long> ids);
}
