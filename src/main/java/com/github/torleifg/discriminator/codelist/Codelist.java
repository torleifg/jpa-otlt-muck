package com.github.torleifg.discriminator.codelist;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Codelist {

    @EmbeddedId
    private CodelistId id;

    protected Codelist() {
    }

    protected Codelist(int list, int code) {
        this.id = new CodelistId(list, code);
    }

    public CodelistId getId() {
        return id;
    }
}