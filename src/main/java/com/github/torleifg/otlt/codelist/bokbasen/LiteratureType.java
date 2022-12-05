package com.github.torleifg.otlt.codelist.bokbasen;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("55")
public class LiteratureType extends BokbasenCodelist {

    protected LiteratureType() {
    }

    private LiteratureType(int code) {
        super(1, code);
    }

    public static LiteratureType of(int code) {
        return new LiteratureType(code);
    }
}