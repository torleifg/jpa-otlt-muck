package com.github.torleifg.discriminator.work;

import com.github.torleifg.discriminator.codelist.IntellectualLevel;
import com.github.torleifg.discriminator.codelist.LiteratureType;

import javax.persistence.*;
import java.util.*;

@Entity
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private final Set<LiteratureType> literatureType = new HashSet<>();

    @ManyToMany
    private final Set<IntellectualLevel> intellectualLevel = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Set<LiteratureType> getLiteratureType() {
        return new HashSet<>(this.literatureType);
    }

    public void addLiteratureType(LiteratureType literatureType) {
        this.literatureType.add(literatureType);
    }

    public void removeLiteratureType(LiteratureType literatureType) {
        this.literatureType.removeIf(type -> type.getId().equals(literatureType.getId()));
    }

    public Set<IntellectualLevel> getIntellectualLevel() {
        return new HashSet<>(this.intellectualLevel);
    }

    public void addIntellectualLevel(IntellectualLevel intellectualLevel) {
        this.intellectualLevel.add(intellectualLevel);
    }

    public void removeIntellectualLevel(IntellectualLevel intellectualLevel) {
        this.intellectualLevel.removeIf(type -> type.getId().equals(intellectualLevel.getId()));
    }
}