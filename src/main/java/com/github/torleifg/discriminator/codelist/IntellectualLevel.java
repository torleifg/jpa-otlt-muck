package com.github.torleifg.discriminator.codelist;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class IntellectualLevel extends Codelist {

    protected IntellectualLevel() {
    }

    private IntellectualLevel(int code) {
        super(2, code);
    }

    public static IntellectualLevel of(int code) {
        return new IntellectualLevel(code);
    }
}