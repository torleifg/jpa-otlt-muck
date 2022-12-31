package com.github.torleifg.otlt;

import com.github.torleifg.otlt.codelist.bokbasen.IntellectualLevel;
import com.github.torleifg.otlt.codelist.bokbasen.LiteratureType;
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
        final var literatureType = testEntityManager.persistFlushFind(LiteratureType.of(1));

        final var work = new Work();
        work.addLiteratureType(literatureType);
        final var id = testEntityManager.persistAndGetId(work, Long.class);

        testEntityManager.flush();
        testEntityManager.clear();

        final var workById = repostitory.findById(id).orElseThrow();

        final var literatureTypeById = testEntityManager.find(LiteratureType.class, literatureType.getId());
        workById.removeLiteratureType(literatureTypeById);

        repostitory.saveAndFlush(workById);

        assertEquals(0, workById.getLiteratureType().size());
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void addInvalidIntellectualLevelThrowsExceptionTest() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            final var work = new Work();
            work.addIntellectualLevel(IntellectualLevel.of(2));
            repostitory.save(work);
        });
    }
}