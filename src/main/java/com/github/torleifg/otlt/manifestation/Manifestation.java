package com.github.torleifg.otlt.manifestation;

import com.github.torleifg.otlt.common.Title;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Manifestation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "manifestation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Title> title = new ArrayList<>();

    public Long getId() {
        return id;
    }
}