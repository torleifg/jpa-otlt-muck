package com.github.torleifg.otlt.codelist;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
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

    public int getCode() {
        return code;
    }
}