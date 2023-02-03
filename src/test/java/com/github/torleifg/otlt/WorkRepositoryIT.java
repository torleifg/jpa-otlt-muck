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
    private TestEntityManager entityManager;

    @Test
    void addIntellectualLevelToWorkTest() {
        final var intellectualLevel = entityManager.persist(IntellectualLevel.of(1));

        final var work = new Work();
        work.addIntellectualLevel(intellectualLevel);
        repostitory.save(work);

        assertEquals(work, entityManager.find(Work.class, work.getId()));
        assertEquals(1, entityManager.find(Work.class, work.getId()).getIntellectualLevel().size());
    }

    @Test
    void addAndRemoveLiteratureTypeFromWorkTest() {
        final var firstLiteratureType = entityManager.persist(LiteratureType.of(1));
        final var secondLiteratureType = entityManager.persist(LiteratureType.of(2));

        final var work = new Work();
        work.addLiteratureType(firstLiteratureType);
        work.addLiteratureType(secondLiteratureType);
        repostitory.save(work);

        work.removeLiteratureType(firstLiteratureType);

        assertEquals(work, entityManager.find(Work.class, work.getId()));
        assertEquals(1, entityManager.find(Work.class, work.getId()).getLiteratureType().size());
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