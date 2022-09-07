package com.kreitek.jhipster.service.dto;

import java.io.Serializable;

public interface AlbumSlimDTO {
    Long getId();
    String getTitle();
    Long getArtistId();
    String getArtistName();
    Long getStyleId();
    String getStyleName();
}
