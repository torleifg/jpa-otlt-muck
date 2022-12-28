package com.github.torleifg.otlt;

import com.github.torleifg.otlt.codelist.bokbasen.BokbasenCodelist;
import com.github.torleifg.otlt.codelist.bokbasen.BokbasenCodelistRepository;
import com.github.torleifg.otlt.codelist.bokbasen.IntellectualLevel;
import com.github.torleifg.otlt.codelist.bokbasen.LiteratureType;
import com.github.torleifg.otlt.codelist.CodelistId;
import com.github.torleifg.otlt.work.Work;
import com.github.torleifg.otlt.work.WorkRepostitory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WorkRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private WorkRepostitory workRepostitory;

    @Autowired
    private BokbasenCodelistRepository<? super BokbasenCodelist> bokbasenCodelistRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void addIntellectualLevelAndLiteratureTypeTest() {
        final var intellectualLevel = IntellectualLevel.of(1);
        testEntityManager.persist(intellectualLevel);

        final var literatureType = LiteratureType.of(1);
        testEntityManager.persist(literatureType);

        final var work = new Work();
        work.addIntellectualLevel(intellectualLevel);
        work.addLiteratureType(literatureType);
        testEntityManager.persistAndFlush(work);

        testEntityManager.clear();

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
    void addIntellectualLevelAndLiteratureTypeAndRemoveLiteratureTypeTest() {
        final var intellectualLevel = IntellectualLevel.of(1);
        testEntityManager.persist(intellectualLevel);

        final var literatureType = LiteratureType.of(1);
        testEntityManager.persist(literatureType);

        final var work = new Work();
        work.addIntellectualLevel(intellectualLevel);
        work.addLiteratureType(literatureType);
        testEntityManager.persistAndFlush(work);

        work.removeLiteratureType(literatureType);
        testEntityManager.persistAndFlush(work);

        testEntityManager.clear();

        final var first = workRepostitory.findById(work.getId());
        assertTrue(first.isPresent());
        assertEquals(1, work.getIntellectualLevel().size());
        assertEquals(0, work.getLiteratureType().size());
    }

    @Test
    void addInvalidIntellectualLevelThrowsExceptionTest() {
        final var intellectualLevel = IntellectualLevel.of(1);
        testEntityManager.persist(intellectualLevel);

        assertThrows(DataIntegrityViolationException.class, () -> {
            final var work = new Work();
            work.addIntellectualLevel(intellectualLevel);
            work.addIntellectualLevel(IntellectualLevel.of(2));
            workRepostitory.saveAndFlush(work);
        });
    }
}