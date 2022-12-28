package com.github.torleifg.otlt;

import com.github.torleifg.otlt.codelist.CodelistId;
import com.github.torleifg.otlt.codelist.onix.OnixCodelist;
import com.github.torleifg.otlt.codelist.onix.OnixCodelistRepository;
import com.github.torleifg.otlt.codelist.onix.ProductContentType;
import com.github.torleifg.otlt.expression.Expression;
import com.github.torleifg.otlt.expression.ExpressionRepository;
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
    private OnixCodelistRepository<? super OnixCodelist> onixCodelistRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void addProductContentTypeTest() {
        final var productContentType = ProductContentType.of(1);
        testEntityManager.persist(productContentType);

        final var expression = new Expression();
        expression.addProductContentType(productContentType);
        testEntityManager.persistAndFlush(expression);

        testEntityManager.clear();

        final var first = expressionRepository.findById(expression.getId());
        assertTrue(first.isPresent());
        assertEquals(1, first.get().getProductContentType().size());

        final var code = first.get().getProductContentType().stream()
                .map(ProductContentType::getId)
                .map(CodelistId::getCode)
                .findFirst();

        assertTrue(code.isPresent());
        assertEquals(1, code.get());

        final var second = onixCodelistRepository.findById(productContentType.getId());
        assertTrue(second.isPresent());
        assertTrue(second.get() instanceof ProductContentType);

        final var id = ((ProductContentType) second.get()).getExpression().stream()
                .map(Expression::getId)
                .findFirst();

        assertTrue(id.isPresent());
        assertEquals(expression.getId(), id.get());
    }

    @Test
    void addAndRemoveProductContentTypeTest() {
        final var productContentType = ProductContentType.of(1);
        testEntityManager.persist(productContentType);

        final var expression = new Expression();
        expression.addProductContentType(productContentType);
        testEntityManager.persistAndFlush(expression);

        expression.removeProductContentType(productContentType);
        testEntityManager.persistAndFlush(expression);

        testEntityManager.clear();

        final var first = expressionRepository.findById(expression.getId());
        assertTrue(first.isPresent());
        assertEquals(0, first.get().getProductContentType().size());
    }

    @Test
    void addInvalidProductContentTypeThrowsExceptionTest() {
        final var productContentType = ProductContentType.of(1);
        testEntityManager.persist(productContentType);

        assertThrows(DataIntegrityViolationException.class, () -> {
            final var expression = new Expression();
            expression.addProductContentType(ProductContentType.of(2));
            expressionRepository.saveAndFlush(expression);

        });
    }
}