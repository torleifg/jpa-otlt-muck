package com.github.torleifg.discriminator.codelist;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "list")
public class CodelistId implements Serializable {

    @Column
    private int list;

    @Column
    private int code;

    protected CodelistId() {
    }

    public CodelistId(int list, int code) {
        this.list = list;
        this.code = code;
    }

    public int getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodelistId that)) return false;
        return list == that.list && code == that.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, code);
    }
}