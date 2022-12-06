package com.github.torleifg.otlt.codelist.bokbasen;

import com.github.torleifg.otlt.codelist.CodelistId;
import org.hibernate.annotations.DiscriminatorFormula;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula(value = "list")
public abstract class BokbasenCodelist {

    @EmbeddedId
    private CodelistId id;

    protected BokbasenCodelist() {
    }

    protected BokbasenCodelist(int list, int code) {
        this.id = new CodelistId(list, code);
    }

    public CodelistId getId() {
        return id;
    }
}