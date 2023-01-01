package com.github.torleifg.otlt.codelist;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof CodelistId that)) {
            return false;
        }

        return list == that.list && code == that.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, code);
    }
}