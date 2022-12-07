package com.github.torleifg.otlt.codelist.bokbasen;

import com.github.torleifg.otlt.work.Work;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("55")
public class LiteratureType extends BokbasenCodelist {

    @ManyToMany(mappedBy = "literatureType")
    private final Set<Work> work = new HashSet<>();

    protected LiteratureType() {
    }

    private LiteratureType(int code) {
        super(55, code);
    }

    public static LiteratureType of(int code) {
        return new LiteratureType(code);
    }

    public Set<Work> getWork() {
        return work;
    }
}