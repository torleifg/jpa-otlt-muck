package com.github.torleifg.discriminator;

import com.github.torleifg.discriminator.codelist.CodelistRepository;
import com.github.torleifg.discriminator.codelist.LiteratureType;
import com.github.torleifg.discriminator.expression.Expression;
import com.github.torleifg.discriminator.expression.ExpressionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ExpressionRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private ExpressionRepository expressionRepository;

    @Autowired
    private CodelistRepository codelistRepository;

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

        expression = expressionRepository.save(expression);
        assertEquals(1, expression.getLiteratureType().size());
    }

    @Test
    void givenExpressionWithLiteratureTypeWhenLiteratureTypeIsRemovedThenSavingExpressionWillRemoveLiteratureTypeAndJuntionTableRow() {
        codelistRepository.save(LiteratureType.of(1));

        var expression = new Expression();
        expression.addLiteratureType(LiteratureType.of(1));

        expression = expressionRepository.save(expression);
        assertEquals(1, expression.getLiteratureType().size());

        expression.removeLiteratureType(LiteratureType.of(1));
        assertEquals(0, expression.getLiteratureType().size());
    }

    @Test
    void givenInvalidCodeWhenCreateExpressionThenExceptionIsThrown() {
        codelistRepository.save(LiteratureType.of(1));

        final var expression = new Expression();
        expression.addLiteratureType(LiteratureType.of(2));

        assertThrows(DataIntegrityViolationException.class, () ->
                expressionRepository.save(expression));
    }
}