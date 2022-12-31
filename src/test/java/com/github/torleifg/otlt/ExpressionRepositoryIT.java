package com.github.torleifg.otlt;

import com.github.torleifg.otlt.expression.ExpressionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ExpressionRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private ExpressionRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void givenWhenThen() {
        assertTrue(true);
    }
}