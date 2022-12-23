package com.github.torleifg.otlt.manifestation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManifestationRepository extends JpaRepository<Manifestation, Long> {
}