package com.github.torleifg.otlt;

import com.github.torleifg.otlt.manifestation.ManifestationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ManifestationRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private ManifestationRepository manifestationRepository;

    @Test
    void test() {

    }
}
