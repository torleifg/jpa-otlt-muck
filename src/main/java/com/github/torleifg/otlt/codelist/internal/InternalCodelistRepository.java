package com.github.torleifg.otlt.codelist.internal;

import com.github.torleifg.otlt.codelist.CodelistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternalCodelistRepository<T extends InternalCodelist> extends JpaRepository<T, CodelistId> {

    @Query("from LiteratureType")
    List<LiteratureType> findLiteratureTypes();

    @Query("from LiteratureType where id.code=?1")
    Optional<LiteratureType> findLiteratureTypeByCode(int code);

    @Query("from IntellectualLevel")
    List<IntellectualLevel> findIntellectualLevels();

    @Query("from IntellectualLevel where id.code=?1")
    Optional<IntellectualLevel> findIntellectualLevelByCode(int code);
}