package com.github.torleifg.otlt.codelist.onix;

import com.github.torleifg.otlt.codelist.CodelistId;

import javax.persistence.*;

@Entity
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class OnixCodelist {

    @EmbeddedId
    private CodelistId id;

    protected OnixCodelist() {
    }

    protected OnixCodelist(int list, int code) {
        this.id = new CodelistId(list, code);
    }

    public CodelistId getId() {
        return id;
    }
}