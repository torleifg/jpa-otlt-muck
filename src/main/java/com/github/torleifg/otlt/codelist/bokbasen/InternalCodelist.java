package com.github.torleifg.otlt.codelist.bokbasen;

import com.github.torleifg.otlt.codelist.CodelistId;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "list", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorOptions(insert = false)
public abstract class InternalCodelist {

    @EmbeddedId
    private CodelistId id;

    protected InternalCodelist() {
    }

    public InternalCodelist(int list, int code) {
        this.id = new CodelistId(list, code);
    }

    public CodelistId getId() {
        return id;
    }
}