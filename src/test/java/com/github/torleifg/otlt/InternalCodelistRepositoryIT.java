package com.github.torleifg.otlt;

import com.github.torleifg.otlt.codelist.internal.IntellectualLevel;
import com.github.torleifg.otlt.codelist.internal.InternalCodelist;
import com.github.torleifg.otlt.codelist.internal.InternalCodelistRepository;
import com.github.torleifg.otlt.codelist.internal.LiteratureType;
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
    private TestEntityManager testEntityManager;

    @Test
    void customFindAllIntellectualLevelsQueryTest() {
        testEntityManager.persistAndFlush(IntellectualLevel.of(1));
        testEntityManager.clear();

        final var intellectualLevels = repository.findIntellectualLevels();

        assertEquals(1, intellectualLevels.size());
    }

    @Test
    void customFindOneIntellectualLevelQueryTest() {
        testEntityManager.persistAndFlush(IntellectualLevel.of(1));
        testEntityManager.clear();

        final var intellectualLevel = repository.findIntellectualLevelByCode(1);

        assertTrue(intellectualLevel.isPresent());
    }

    @Test
    void customFindAllLiteratureTypesQueryTest() {
        testEntityManager.persistAndFlush(LiteratureType.of(1));
        testEntityManager.clear();

        final var literatureTypes = repository.findLiteratureTypes();

        assertEquals(1, literatureTypes.size());
    }

    @Test
    void customFindOneLiteratureTypeQueryTest() {
        testEntityManager.persistAndFlush(LiteratureType.of(1));
        testEntityManager.clear();

        final var literatureType = repository.findLiteratureTypeByCode(1);

        assertTrue(literatureType.isPresent());
    }
}