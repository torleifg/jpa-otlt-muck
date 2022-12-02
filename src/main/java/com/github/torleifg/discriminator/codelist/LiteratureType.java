package com.github.torleifg.discriminator.codelist;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class LiteratureType extends Codelist {

    protected LiteratureType() {
    }

    private LiteratureType(int code) {
        super(1, code);
    }

    public static LiteratureType of(int code) {
        return new LiteratureType(code);
    }
}