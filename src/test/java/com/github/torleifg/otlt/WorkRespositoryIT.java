package com.github.torleifg.otlt;

import com.github.torleifg.otlt.codelist.bokbasen.BokbasenCodelist;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WorkRespositoryIT extends AbstractIntegrationTest {

    @Autowired
    private WorkRepostitory workRepostitory;

    @Autowired
    private BokbasenCodelistRepository<? super BokbasenCodelist> bokbasenCodelistRepository;

    @BeforeEach
    void setup() {
        workRepostitory.deleteAll();
        bokbasenCodelistRepository.deleteAll();
    }

    @Test
    void givenValidCodesWhenCreateWorkThenWorkIsSaved() {
        final var intellectualLevel = bokbasenCodelistRepository.save(IntellectualLevel.of(1));
        assertNotNull(intellectualLevel.getId());

        final var literatureType  = bokbasenCodelistRepository.save(LiteratureType.of(1));
        assertNotNull(literatureType.getId());

        final var work = new Work();
        work.addIntellectualLevel(intellectualLevel);
        work.addLiteratureType(literatureType);
        workRepostitory.save(work);
        assertNotNull(work.getId());

        final var first = workRepostitory.findById(work.getId());
        assertTrue(first.isPresent());
        assertEquals(1, first.get().getIntellectualLevel().size());
        assertEquals(1, first.get().getLiteratureType().size());

        final var code = work.getIntellectualLevel().stream()
                .map(IntellectualLevel::getId)
                .map(CodelistId::getCode)
                .findFirst();

        assertTrue(code.isPresent());
        assertEquals(1, code.get());

        final var second = bokbasenCodelistRepository.findById(literatureType.getId());
        assertTrue(second.isPresent());
        assertTrue(second.get() instanceof LiteratureType);

        final var id = ((LiteratureType) second.get()).getWork().stream()
                .map(Work::getId)
                .findFirst();

        assertTrue(id.isPresent());
        assertEquals(work.getId(), id.get());
    }

    @Test
    void givenWorkWithLiteratureTypeWhenLiteratureTypeIsRemovedThenSavingWorkWillRemoveLiteratureTypeAndJunctionTableRow() {
        final var intellectualLevel = bokbasenCodelistRepository.save(IntellectualLevel.of(1));
        assertNotNull(intellectualLevel.getId());

        final var literatureType  = bokbasenCodelistRepository.save(LiteratureType.of(1));
        assertNotNull(literatureType.getId());

        final var work = new Work();
        work.addIntellectualLevel(intellectualLevel);
        work.addLiteratureType(literatureType);
        workRepostitory.save(work);
        assertNotNull(work.getId());

        final var first = workRepostitory.findById(work.getId());
        assertTrue(first.isPresent());
        assertEquals(1, first.get().getIntellectualLevel().size());
        assertEquals(1, first.get().getLiteratureType().size());

        first.ifPresent(exp -> exp.removeLiteratureType(literatureType));

        final var second = workRepostitory.findById(work.getId());
        assertTrue(second.isPresent());
        assertEquals(1, work.getIntellectualLevel().size());
        assertEquals(0, work.getLiteratureType().size());
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void givenInvalidCodeWhenCreateWorkThenExceptionIsThrown() {
        final var intellectualLevel = bokbasenCodelistRepository.save(IntellectualLevel.of(1));
        assertNotNull(intellectualLevel.getId());

        final var work = new Work();
        work.addIntellectualLevel(intellectualLevel);
        work.addLiteratureType(LiteratureType.of(1));

        assertThrows(DataIntegrityViolationException.class, () ->
                workRepostitory.save(work));
    }
}