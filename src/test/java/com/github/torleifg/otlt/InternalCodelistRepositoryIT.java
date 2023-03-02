package com.github.torleifg.otlt;

import com.github.torleifg.otlt.codelist.internal.IntellectualLevel;
import com.github.torleifg.otlt.codelist.internal.InternalCodelist;
import com.github.torleifg.otlt.codelist.internal.InternalCodelistRepository;
import com.github.torleifg.otlt.codelist.internal.LiteratureType;
import com.github.torleifg.otlt.work.Work;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InternalCodelistRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private InternalCodelistRepository<? super InternalCodelist> repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void customFindAllIntellectualLevelsQueryTest() {
        entityManager.persist(IntellectualLevel.of(1));
        entityManager.persist(IntellectualLevel.of(2));

        final var intellectualLevels = repository.findIntellectualLevels();

        assertEquals(2, intellectualLevels.size());
    }

    @Test
    void customFindOneIntellectualLevelQueryTest() {
        entityManager.persist(IntellectualLevel.of(1));

        final var intellectualLevel = repository.findIntellectualLevelByCode(1);

        assertTrue(intellectualLevel.isPresent());
    }

    @Test
    void customFindAllLiteratureTypesQueryTest() {
        entityManager.persist(LiteratureType.of(1));
        entityManager.persist(LiteratureType.of(2));

        final var literatureTypes = repository.findLiteratureTypes();

        assertEquals(2, literatureTypes.size());
    }

    @Test
    void customFindOneLiteratureTypeQueryTest() {
        entityManager.persist(LiteratureType.of(1));

        final var literatureType = repository.findLiteratureTypeByCode(1);

        assertTrue(literatureType.isPresent());
    }

    @Test
    void customFindOneLiteratureTypeWithWorkTest() {
        final var literatureType = entityManager.persist(LiteratureType.of(1));

        final var work = new Work();
        work.addLiteratureType(literatureType);
        entityManager.persist(work);

        final var optionalLiteratureType = repository.findLiteratureTypeByCode(1);

        assertTrue(optionalLiteratureType.isPresent());
        assertEquals(1, optionalLiteratureType.get().getWork().size());
    }

    @Test
    void cacheFindAllIntellectualLevelsQueryTest() {
        entityManager.persist(IntellectualLevel.of(1));
        entityManager.persist(IntellectualLevel.of(2));

        repository.findIntellectualLevels();
        repository.findIntellectualLevels();
    }

    @Test
    void cacheFindOneIntellectualLevelQueryTest() {
        entityManager.persist(IntellectualLevel.of(1));

        repository.findIntellectualLevelByCode(1);
        repository.findIntellectualLevelByCode(1);
    }

    @Test
    void cacheFindAllLiteratureTypesQueryTest() {
        entityManager.persist(LiteratureType.of(1));
        entityManager.persist(LiteratureType.of(2));

        repository.findLiteratureTypes();
        repository.findLiteratureTypes();
    }

    @Test
    void cacheFindOneLiteratureTypeQueryTest() {
        entityManager.persist(LiteratureType.of(1));

        repository.findLiteratureTypeByCode(1);
        repository.findLiteratureTypeByCode(1);
    }
}