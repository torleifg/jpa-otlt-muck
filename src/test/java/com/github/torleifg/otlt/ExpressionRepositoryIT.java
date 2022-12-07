package com.github.torleifg.otlt;

import com.github.torleifg.otlt.codelist.CodelistId;
import com.github.torleifg.otlt.codelist.onix.OnixCodelist;
import com.github.torleifg.otlt.codelist.onix.OnixCodelistRepository;
import com.github.torleifg.otlt.codelist.onix.ProductContentType;
import com.github.torleifg.otlt.expression.Expression;
import com.github.torleifg.otlt.expression.ExpressionRepository;
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
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExpressionRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private ExpressionRepository expressionRepository;

    @Autowired
    private OnixCodelistRepository<? super OnixCodelist> onixCodelistRepository;

    @BeforeEach
    void setup() {
        expressionRepository.deleteAll();
        onixCodelistRepository.deleteAll();
    }

    @Test
    void givenValidCodesWhenCreateExpressionThenExpressionIsSaved() {
        final var productContentType = onixCodelistRepository.save(ProductContentType.of(1));

        final var expression = new Expression();
        expression.addProductContentType(productContentType);

        expressionRepository.save(expression);
        assertEquals(1, expression.getProductContentType().size());

        final var code = expression.getProductContentType().stream()
                .map(ProductContentType::getId)
                .map(CodelistId::getCode)
                .findFirst();

        assertTrue(code.isPresent());
        assertEquals(1, code.get());

        final var id = productContentType.getExpression().stream()
                .map(Expression::getId)
                .findFirst();

        assertTrue(id.isPresent());
    }

    @Test
    void givenExpressionWithLiteratureTypeWhenLiteratureTypeIsRemovedThenSavingExpressionWillRemoveLiteratureTypeAndJunctionTableRow() {
        final var productContentType = onixCodelistRepository.save(ProductContentType.of(1));

        final var expression = new Expression();
        expression.addProductContentType(productContentType);

        expressionRepository.save(expression);
        assertEquals(1, expression.getProductContentType().size());

        expression.removeProductContentType(productContentType);

        expressionRepository.save(expression);
        assertEquals(0, expression.getProductContentType().size());
        assertEquals(0, productContentType.getExpression().size());
    }

    @Test
    void givenInvalidCodeWhenCreateExpressionThenExceptionIsThrown() {
        onixCodelistRepository.save(ProductContentType.of(1));

        final var expression = new Expression();
        expression.addProductContentType(ProductContentType.of(2));

        assertThrows(DataIntegrityViolationException.class, () ->
                expressionRepository.save(expression));
    }
}