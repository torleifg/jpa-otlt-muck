package com.github.torleifg.discriminator;

import com.github.torleifg.discriminator.codelist.CodelistId;
import com.github.torleifg.discriminator.codelist.CodelistRepository;
import com.github.torleifg.discriminator.codelist.IntellectualLevel;
import com.github.torleifg.discriminator.codelist.LiteratureType;
import com.github.torleifg.discriminator.work.Work;
import com.github.torleifg.discriminator.work.WorkRepostitory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WorkRespositoryIT extends AbstractIntegrationTest {

    @Autowired
    private WorkRepostitory workRepostitory;

    @Autowired
    private CodelistRepository codelistRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setup() {
        workRepostitory.deleteAll();
        codelistRepository.deleteAll();
    }

    @Test
    void givenValidCodesWhenCreateWorkThenWorkIsSaved() {
        codelistRepository.saveAll(Set.of(LiteratureType.of(1), IntellectualLevel.of(1)));

        var work = new Work();
        work.addIntellectualLevel(IntellectualLevel.of(1));
        work.addLiteratureType(LiteratureType.of(1));

        work = workRepostitory.saveAndFlush(work);
        assertEquals(1, work.getLiteratureType().size());
        assertEquals(1, work.getIntellectualLevel().size());

        final var intellectualLevel = work.getIntellectualLevel().stream()
                .map(IntellectualLevel::getId)
                .map(CodelistId::getCode)
                .findFirst();

        assertTrue(intellectualLevel.isPresent());
        assertEquals(1, intellectualLevel.get());
    }

    @Test
    void givenWorkWithLiteratureTypeWhenLiteratureTypeIsRemovedThenSavingWorkWillRemoveLiteratureTypeAndJunctionTableRow() {
        codelistRepository.saveAll(Set.of(LiteratureType.of(1), IntellectualLevel.of(1)));

        var work = new Work();
        work.addIntellectualLevel(IntellectualLevel.of(1));
        work.addLiteratureType(LiteratureType.of(1));

        work = workRepostitory.saveAndFlush(work);
        assertEquals(1, work.getLiteratureType().size());

        work.removeLiteratureType(LiteratureType.of(1));
        work = workRepostitory.saveAndFlush(work);
        assertEquals(0, work.getLiteratureType().size());
    }

    @Test
    void givenInvalidCodeWhenCreateWorkThenExceptionIsThrown() {
        codelistRepository.saveAll(Set.of(LiteratureType.of(1), IntellectualLevel.of(1)));

        var work = new Work();
        work.addIntellectualLevel(IntellectualLevel.of(1));
        work.addLiteratureType(LiteratureType.of(2));

        assertThrows(DataIntegrityViolationException.class, () ->
                workRepostitory.saveAndFlush(work));
    }
}