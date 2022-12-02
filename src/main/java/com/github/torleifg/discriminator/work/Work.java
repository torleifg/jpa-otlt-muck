package com.github.torleifg.discriminator.work;

import com.github.torleifg.discriminator.codelist.IntellectualLevel;
import com.github.torleifg.discriminator.codelist.LiteratureType;

import javax.persistence.*;
import java.util.*;

@Entity
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToMany
    private final Set<LiteratureType> literatureTypes = new HashSet<>();

    @ManyToMany
    private final Set<IntellectualLevel> intellectualLevels = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public Set<LiteratureType> getLiteratureTypes() {
        return new HashSet<>(this.literatureTypes);
    }

    public void addLiteratureType(LiteratureType literatureType) {
        this.literatureTypes.add(literatureType);
    }

    public void removeLiteratureType(LiteratureType literatureType) {
        this.literatureTypes.removeIf(type -> type.getId().equals(literatureType.getId()));
    }

    public Set<IntellectualLevel> getIntellectualLevels() {
        return new HashSet<>(this.intellectualLevels);
    }

    public void addIntellectualLevel(IntellectualLevel intellectualLevel) {
        this.intellectualLevels.add(intellectualLevel);
    }

    public void removeIntellectualLevel(IntellectualLevel intellectualLevel) {
        this.intellectualLevels.removeIf(type -> type.getId().equals(intellectualLevel.getId()));
    }
}