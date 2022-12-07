package com.github.torleifg.otlt.codelist;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@EqualsAndHashCode
@Embeddable
public class CodelistId implements Serializable {
    private int list;
    private int code;

    protected CodelistId() {
    }

    public CodelistId(int list, int code) {
        this.list = list;
        this.code = code;
    }
}