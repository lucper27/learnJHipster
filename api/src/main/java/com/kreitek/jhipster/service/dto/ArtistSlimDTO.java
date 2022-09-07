package com.kreitek.jhipster.service.dto;

import org.hibernate.annotations.Immutable;


import java.io.Serializable;
public class ArtistSlimDTO implements Serializable {

    private Long id;

    private String name;

    public ArtistSlimDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
