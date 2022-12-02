package com.github.torleifg.discriminator.codelist;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "codelist")
public class CodelistId implements Serializable {

    @Column
    private int codelist;

    @Column
    private int code;

    protected CodelistId() {
    }

    public CodelistId(int codelist, int code) {
        this.codelist = codelist;
        this.code = code;
    }

    public int getCodelist() {
        return codelist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodelistId that)) return false;
        return codelist == that.codelist && code == that.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codelist, code);
    }
}