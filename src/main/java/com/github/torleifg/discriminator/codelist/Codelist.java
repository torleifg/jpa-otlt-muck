package com.github.torleifg.discriminator.codelist;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Codelist {

    @EmbeddedId
    private CodelistId id;

    protected Codelist() {
    }

    protected Codelist(int codelist, int code) {
        this.id = new CodelistId(codelist, code);
    }

    public CodelistId getId() {
        return id;
    }
}