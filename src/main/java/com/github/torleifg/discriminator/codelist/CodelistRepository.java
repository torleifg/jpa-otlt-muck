package com.github.torleifg.discriminator.codelist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodelistRepository extends JpaRepository<Codelist, CodelistId> {
}