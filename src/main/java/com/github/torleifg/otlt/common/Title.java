package com.github.torleifg.otlt.common;

import com.github.torleifg.otlt.codelist.bokbasen.TitleType;
import com.github.torleifg.otlt.manifestation.Manifestation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TitleType titleType;

    private int titleOrder;
    private String value;

    @ManyToOne
    private Manifestation manifestation;

    @ManyToOne
    private Title parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Title> children = new ArrayList<>();

    public Long getId() {
        return id;
    }
}
