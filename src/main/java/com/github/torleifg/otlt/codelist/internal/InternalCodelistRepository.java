package com.github.torleifg.otlt.codelist.internal;

import com.github.torleifg.otlt.codelist.CodelistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

import static org.hibernate.jpa.QueryHints.HINT_CACHEABLE;

@Repository
public interface InternalCodelistRepository<T extends InternalCodelist> extends JpaRepository<T, CodelistId> {

    @QueryHints({@QueryHint(name = HINT_CACHEABLE, value = "true")})
    @Query("from LiteratureType")
    List<LiteratureType> findLiteratureTypes();

    @QueryHints({@QueryHint(name = HINT_CACHEABLE, value = "true")})
    @Query("from LiteratureType where id.code=?1")
    Optional<LiteratureType> findLiteratureTypeByCode(int code);

    @QueryHints({@QueryHint(name = HINT_CACHEABLE, value = "true")})
    @Query("from IntellectualLevel")
    List<IntellectualLevel> findIntellectualLevels();

    @QueryHints({@QueryHint(name = HINT_CACHEABLE, value = "true")})
    @Query("from IntellectualLevel where id.code=?1")
    Optional<IntellectualLevel> findIntellectualLevelByCode(int code);
}