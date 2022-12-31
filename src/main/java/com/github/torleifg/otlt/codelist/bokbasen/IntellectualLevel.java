package com.github.torleifg.otlt.codelist.bokbasen;

import com.github.torleifg.otlt.work.Work;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("69")
public class IntellectualLevel extends InternalCodelist {

    @ManyToMany(mappedBy = "intellectualLevel")
    private final Set<Work> work = new HashSet<>();

    protected IntellectualLevel() {
    }

    private IntellectualLevel(int code) {
        super(69, code);
    }

    public static IntellectualLevel of(int code) {
        return new IntellectualLevel(code);
    }

    public Set<Work> getWork() {
        return work;
    }
}