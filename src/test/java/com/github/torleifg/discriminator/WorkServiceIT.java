package com.github.torleifg.discriminator;

import com.github.torleifg.discriminator.codelist.CodelistRepository;
import com.github.torleifg.discriminator.codelist.IntellectualLevel;
import com.github.torleifg.discriminator.codelist.LiteratureType;
import com.github.torleifg.discriminator.work.WorkRepostitory;
import com.github.torleifg.discriminator.work.WorkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WorkServiceIT extends AbstractIntegrationTest {

    @Autowired
    private WorkService service;

    @Autowired
    private WorkRepostitory workRepostitory;

    @Autowired
    private CodelistRepository codelistRepository;

    @BeforeEach
    void setup() {
        workRepostitory.deleteAll();
        codelistRepository.deleteAll();
    }

    @Test
    void givenValidCodesWhenCreateWorkThenWorkIsSaved() {
        codelistRepository.saveAll(Set.of(LiteratureType.of(1), IntellectualLevel.of(1)));

        final var work = service.createWork(Set.of(1), Set.of(1));

        assertEquals(1, work.getLiteratureTypes().size());
        assertEquals(1, work.getIntellectualLevels().size());
    }

    @Test
    void givenInvalidCodeWhenCreateWorkThenExceptionIsThrown() {
        codelistRepository.saveAll(Set.of(LiteratureType.of(1), IntellectualLevel.of(1)));

        assertThrows(DataIntegrityViolationException.class, () ->
                service.createWork(Set.of(1), Set.of(2)));
    }
}