package com.github.torleifg.otlt.codelist.bokbasen;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("69")
public class IntellectualLevel extends BokbasenCodelist {

    protected IntellectualLevel() {
    }

    private IntellectualLevel(int code) {
        super(2, code);
    }

    public static IntellectualLevel of(int code) {
        return new IntellectualLevel(code);
    }
}