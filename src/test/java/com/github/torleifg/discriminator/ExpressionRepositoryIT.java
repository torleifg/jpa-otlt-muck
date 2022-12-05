package com.github.torleifg.discriminator;

import com.github.torleifg.discriminator.codelist.CodelistId;
import com.github.torleifg.discriminator.codelist.CodelistRepository;
import com.github.torleifg.discriminator.codelist.LiteratureType;
import com.github.torleifg.discriminator.expression.Expression;
import com.github.torleifg.discriminator.expression.ExpressionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExpressionRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private ExpressionRepository expressionRepository;

    @Autowired
    private CodelistRepository codelistRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setup() {
        expressionRepository.deleteAll();
        codelistRepository.deleteAll();
    }

    @Test
    void givenValidCodesWhenCreateExpressionThenExpressionIsSaved() {
        codelistRepository.save(LiteratureType.of(1));

        var expression = new Expression();
        expression.addLiteratureType(LiteratureType.of(1));

        expression = expressionRepository.saveAndFlush(expression);
        assertEquals(1, expression.getLiteratureType().size());

        final var literatureType = expression.getLiteratureType().stream()
                .map(LiteratureType::getId)
                .map(CodelistId::getCode)
                .findFirst();

        assertTrue(literatureType.isPresent());
        assertEquals(1, literatureType.get());
    }

    @Test
    void givenExpressionWithLiteratureTypeWhenLiteratureTypeIsRemovedThenSavingExpressionWillRemoveLiteratureTypeAndJunctionTableRow() {
        codelistRepository.save(LiteratureType.of(1));

        var expression = new Expression();
        expression.addLiteratureType(LiteratureType.of(1));

        expression = expressionRepository.saveAndFlush(expression);
        assertEquals(1, expression.getLiteratureType().size());

        expression.removeLiteratureType(LiteratureType.of(1));
        expression = expressionRepository.saveAndFlush(expression);
        assertEquals(0, expression.getLiteratureType().size());
    }

    @Test
    void givenInvalidCodeWhenCreateExpressionThenExceptionIsThrown() {
        codelistRepository.save(LiteratureType.of(1));

        var expression = new Expression();
        expression.addLiteratureType(LiteratureType.of(2));

        assertThrows(DataIntegrityViolationException.class, () ->
                expressionRepository.saveAndFlush(expression));
    }
}