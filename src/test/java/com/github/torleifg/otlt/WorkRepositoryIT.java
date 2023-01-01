package com.github.torleifg.otlt;

import com.github.torleifg.otlt.codelist.internal.IntellectualLevel;
import com.github.torleifg.otlt.codelist.internal.LiteratureType;
import com.github.torleifg.otlt.work.Work;
import com.github.torleifg.otlt.work.WorkRepostitory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WorkRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private WorkRepostitory repostitory;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void addIntellectualLevelToWorkTest() {
        testEntityManager.persistAndFlush(IntellectualLevel.of(1));
        testEntityManager.clear();

        final var work = new Work();
        work.addIntellectualLevel(IntellectualLevel.of(1));
        final var workWithIntellectualLevel = repostitory.saveAndFlush(work);

        assertEquals(1, workWithIntellectualLevel.getIntellectualLevel().size());
    }

    @Test
    void addAndRemoveLiteratureTypeFromWorkTest() {
        testEntityManager.persist(LiteratureType.of(1));

        final var work = new Work();
        work.addLiteratureType(LiteratureType.of(1));
        final var id = testEntityManager.persistAndGetId(work, Long.class);

        testEntityManager.flush();
        testEntityManager.clear();

        final var workByIdOptional = repostitory.findById(id);
        assertTrue(workByIdOptional.isPresent());

        final var workById = workByIdOptional.get();
        workById.removeLiteratureType(LiteratureType.of(1));
        repostitory.saveAndFlush(workById);

        assertEquals(0, workById.getLiteratureType().size());
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void addInvalidIntellectualLevelThrowsExceptionTest() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            final var work = new Work();
            work.addIntellectualLevel(IntellectualLevel.of(1));
            repostitory.save(work);
        });
    }
}