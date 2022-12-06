package com.github.torleifg.otlt;

import com.github.torleifg.otlt.codelist.CodelistId;
import com.github.torleifg.otlt.codelist.onix.OnixCodelistRepository;
import com.github.torleifg.otlt.codelist.onix.ProductContentType;
import com.github.torleifg.otlt.expression.Expression;
import com.github.torleifg.otlt.expression.ExpressionRepository;
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
    private OnixCodelistRepository onixCodelistRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setup() {
        expressionRepository.deleteAll();
        onixCodelistRepository.deleteAll();
    }

    @Test
    void givenValidCodesWhenCreateExpressionThenExpressionIsSaved() {
        onixCodelistRepository.save(ProductContentType.of(1));

        var expression = new Expression();
        expression.addProductContentType(ProductContentType.of(1));

        expression = expressionRepository.saveAndFlush(expression);
        assertEquals(1, expression.getProductContentType().size());

        final var literatureType = expression.getProductContentType().stream()
                .map(ProductContentType::getId)
                .map(CodelistId::getCode)
                .findFirst();

        assertTrue(literatureType.isPresent());
        assertEquals(1, literatureType.get());
    }

    @Test
    void givenExpressionWithLiteratureTypeWhenLiteratureTypeIsRemovedThenSavingExpressionWillRemoveLiteratureTypeAndJunctionTableRow() {
        onixCodelistRepository.save(ProductContentType.of(1));

        var expression = new Expression();
        expression.addProductContentType(ProductContentType.of(1));

        expressionRepository.saveAndFlush(expression);
        assertEquals(1, expression.getProductContentType().size());

        expression.removeProductContentType(ProductContentType.of(1));

        expressionRepository.saveAndFlush(expression);
        assertEquals(0, expression.getProductContentType().size());
    }

    @Test
    void givenInvalidCodeWhenCreateExpressionThenExceptionIsThrown() {
        onixCodelistRepository.save(ProductContentType.of(1));

        var expression = new Expression();
        expression.addProductContentType(ProductContentType.of(2));

        assertThrows(DataIntegrityViolationException.class, () ->
                expressionRepository.saveAndFlush(expression));
    }
}