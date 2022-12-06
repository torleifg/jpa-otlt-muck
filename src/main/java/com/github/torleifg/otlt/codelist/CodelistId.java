package com.github.torleifg.otlt.codelist;

import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class CodelistId implements Serializable {
    private int list;
    private int code;

    protected CodelistId() {
    }

    public CodelistId(int list, int code) {
        this.list = list;
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}