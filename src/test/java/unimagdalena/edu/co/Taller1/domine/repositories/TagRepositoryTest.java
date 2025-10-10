package unimagdalena.edu.co.Taller1.domine.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import unimagdalena.edu.co.Taller1.domine.entities.Tag;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class TagRepositoryTest extends AbstractRepositoryTI {

    @Autowired
    private TagRepository tagRepository;

    @Test
    @DisplayName("Debe encontrar etiqueta por nombre ignorando mayúsculas/minúsculas")
    void shouldFindByNameIgnoreCase() {
        // GIVEN
        Tag promo = Tag.builder().name("PROMO").build();
        tagRepository.save(promo);

        // WHEN
        Optional<Tag> encontrado = tagRepository.findByNameIgnoreCase("promo");

        // THEN
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getName()).isEqualTo("PROMO");
    }

    @Test
    @DisplayName("Debe retornar etiquetas cuyos nombres estén en la lista dada con límite")
    void shouldFindByNamesWithLimit() {
        // GIVEN
        tagRepository.save(Tag.builder().name("PROMO").build());
        tagRepository.save(Tag.builder().name("VIP").build());
        tagRepository.save(Tag.builder().name("OFERTA").build());

        List<String> names = List.of("PROMO", "VIP", "OFERTA");

        // WHEN
        List<Tag> encontrados = tagRepository.findByNameIn(names);

        // THEN
        assertThat(encontrados).hasSize(2);
        assertThat(encontrados).extracting(Tag::getName)
                .containsAnyOf("PROMO", "VIP", "OFERTA");
    }

    @Test
    @DisplayName("No debe encontrar etiqueta inexistente")
    void shouldReturnEmptyWhenNotFound() {
        // GIVEN
        tagRepository.save(Tag.builder().name("SALE").build());

        // WHEN
        Optional<Tag> notFound = tagRepository.findByNameIgnoreCase("XXX");

        // THEN
        assertThat(notFound).isNotPresent();
    }
}

