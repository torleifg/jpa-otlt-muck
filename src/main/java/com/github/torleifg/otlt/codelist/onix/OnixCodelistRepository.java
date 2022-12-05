package com.github.torleifg.otlt.codelist.onix;

import com.github.torleifg.otlt.codelist.CodelistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnixCodelistRepository extends JpaRepository<OnixCodelist, CodelistId> {
}