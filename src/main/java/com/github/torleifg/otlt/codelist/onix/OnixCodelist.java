package com.github.torleifg.otlt.codelist.onix;

import com.github.torleifg.otlt.codelist.CodelistId;
import org.hibernate.annotations.DiscriminatorFormula;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula(value = "list")
public abstract class OnixCodelist {

    @EmbeddedId
    private CodelistId id;

    protected OnixCodelist() {
    }

    protected OnixCodelist(int list, int code) {
        this.id = new CodelistId(list, code);
    }

    public CodelistId getId() {
        return id;
    }
}
