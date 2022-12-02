package com.github.torleifg.discriminator;

import com.github.torleifg.discriminator.codelist.CodelistRepository;
import com.github.torleifg.discriminator.codelist.LiteratureType;
import com.github.torleifg.discriminator.expression.ExpressionRepository;
import com.github.torleifg.discriminator.expression.ExpressionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ExpressionServiceIT extends AbstractIntegrationTest {

    @Autowired
    private ExpressionService service;

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

        final var expression = service.createExpression(Set.of(1));

        assertEquals(1, expression.getLiteratureTypes().size());
    }

    @Test
    void givenExpressionWithLiteratureTypeWhenLiteratureTypeIsRemovedThenSavingExpressionWillRemoveLiteratureTypeAndJuntionTableRow() {
        codelistRepository.save(LiteratureType.of(1));

        var expression = service.createExpression(Set.of(1));

        assertEquals(1, expression.getLiteratureTypes().size());

        expression.removeLiteratureType(LiteratureType.of(1));

        expression = expressionRepository.save(expression);

        assertEquals(0, expression.getLiteratureTypes().size());
    }

    @Test
    void givenInvalidCodeWhenCreateExpressionThenExceptionIsThrown() {
        assertThrows(DataIntegrityViolationException.class, () ->
                service.createExpression(Set.of(1)));
    }
}