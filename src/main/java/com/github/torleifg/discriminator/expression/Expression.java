package com.github.torleifg.discriminator.expression;

import com.github.torleifg.discriminator.codelist.LiteratureType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Expression {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToMany
    private final Set<LiteratureType> literatureTypes = new HashSet<>();

    public Set<LiteratureType> getLiteratureTypes() {
        return new HashSet<>(this.literatureTypes);
    }

    public void addLiteratureType(LiteratureType literatureType) {
        this.literatureTypes.add(literatureType);
    }

    public void removeLiteratureType(LiteratureType literatureType) {
        this.literatureTypes.removeIf(type -> type.getId().equals(literatureType.getId()));
    }
}