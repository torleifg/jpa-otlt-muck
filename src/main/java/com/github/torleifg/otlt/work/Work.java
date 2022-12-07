package com.github.torleifg.otlt.work;

import com.github.torleifg.otlt.codelist.bokbasen.IntellectualLevel;
import com.github.torleifg.otlt.codelist.bokbasen.LiteratureType;

import javax.persistence.*;
import java.util.*;

@Entity
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "work_literature_type_xref")
    private final Set<LiteratureType> literatureType = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "work_intellectual_level_xref")
    private final Set<IntellectualLevel> intellectualLevel = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Set<LiteratureType> getLiteratureType() {
        return literatureType;
    }

    public void addLiteratureType(LiteratureType literatureType) {
        this.literatureType.add(literatureType);
        literatureType.getWork().add(this);
    }

    public void removeLiteratureType(LiteratureType literatureType) {
        this.literatureType.remove(literatureType);
        literatureType.getWork().remove(this);
    }

    public Set<IntellectualLevel> getIntellectualLevel() {
        return intellectualLevel;
    }

    public void addIntellectualLevel(IntellectualLevel intellectualLevel) {
        this.intellectualLevel.add(intellectualLevel);
        intellectualLevel.getWork().add(this);
    }

    public void removeIntellectualLevel(IntellectualLevel intellectualLevel) {
        this.intellectualLevel.remove(intellectualLevel);
        intellectualLevel.getWork().remove(this);
    }
}