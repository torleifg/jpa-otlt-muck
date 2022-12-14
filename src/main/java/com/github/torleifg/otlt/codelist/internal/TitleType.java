package com.github.torleifg.otlt.codelist.internal;

import com.github.torleifg.otlt.common.Title;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("100")
public class TitleType extends InternalCodelist {

    @OneToMany(mappedBy = "titleType")
    private List<Title> title;

    protected TitleType() {
    }

    private TitleType(int code) {
        super(100, code);
    }

    public static TitleType of(int code) {
        return new TitleType(code);
    }
}