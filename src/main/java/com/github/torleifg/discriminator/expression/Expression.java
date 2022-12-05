package com.github.torleifg.discriminator.expression;

import com.github.torleifg.discriminator.codelist.LiteratureType;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Expression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "expression_literature_type_xref")
    private final Set<LiteratureType> literatureType = new HashSet<>();

    public Set<LiteratureType> getLiteratureType() {
        return Collections.unmodifiableSet(this.literatureType);
    }

    public void addLiteratureType(LiteratureType literatureType) {
        this.literatureType.add(literatureType);
    }

    public void removeLiteratureType(LiteratureType literatureType) {
        this.literatureType.removeIf(type -> type.getId().equals(literatureType.getId()));
    }
}