package com.github.torleifg.otlt.codelist.internal;

import com.github.torleifg.otlt.codelist.CodelistId;

import javax.persistence.*;

@Entity
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class InternalCodelist {

    @EmbeddedId
    private CodelistId id;

    protected InternalCodelist() {
    }

    public InternalCodelist(int list, int code) {
        this.id = new CodelistId(list, code);
    }

    public CodelistId getId() {
        return id;
    }
}