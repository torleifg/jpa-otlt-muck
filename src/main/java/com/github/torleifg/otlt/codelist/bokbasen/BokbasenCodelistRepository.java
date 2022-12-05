package com.github.torleifg.otlt.codelist.bokbasen;

import com.github.torleifg.otlt.codelist.CodelistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BokbasenCodelistRepository extends JpaRepository<BokbasenCodelist, CodelistId> {
}