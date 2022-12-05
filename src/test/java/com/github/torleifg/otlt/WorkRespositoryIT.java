package com.github.torleifg.otlt;

import com.github.torleifg.otlt.codelist.bokbasen.BokbasenCodelistRepository;
import com.github.torleifg.otlt.codelist.bokbasen.IntellectualLevel;
import com.github.torleifg.otlt.codelist.bokbasen.LiteratureType;
import com.github.torleifg.otlt.codelist.CodelistId;
import com.github.torleifg.otlt.work.Work;
import com.github.torleifg.otlt.work.WorkRepostitory;
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
    private BokbasenCodelistRepository bokbasenCodelistRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setup() {
        workRepostitory.deleteAll();
        bokbasenCodelistRepository.deleteAll();
    }

    @Test
    void givenValidCodesWhenCreateWorkThenWorkIsSaved() {
        bokbasenCodelistRepository.saveAllAndFlush(Set.of(LiteratureType.of(1), IntellectualLevel.of(1)));

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
        bokbasenCodelistRepository.saveAllAndFlush(Set.of(LiteratureType.of(1), IntellectualLevel.of(1)));

        var work = new Work();
        work.addIntellectualLevel(IntellectualLevel.of(1));
        work.addLiteratureType(LiteratureType.of(1));
        workRepostitory.saveAndFlush(work);
        assertEquals(1, work.getLiteratureType().size());
        assertEquals(1, work.getIntellectualLevel().size());

        work.removeLiteratureType(LiteratureType.of(1));
        workRepostitory.saveAndFlush(work);
        assertEquals(0, work.getLiteratureType().size());
        assertEquals(1, work.getIntellectualLevel().size());
    }

    @Test
    void givenInvalidCodeWhenCreateWorkThenExceptionIsThrown() {
        bokbasenCodelistRepository.saveAllAndFlush(Set.of(LiteratureType.of(1), IntellectualLevel.of(1)));

        var work = new Work();
        work.addIntellectualLevel(IntellectualLevel.of(1));
        work.addLiteratureType(LiteratureType.of(2));

        assertThrows(DataIntegrityViolationException.class, () ->
                workRepostitory.saveAndFlush(work));
    }
}