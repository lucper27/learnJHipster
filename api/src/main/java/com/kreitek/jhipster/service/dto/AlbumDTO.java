package com.kreitek.jhipster.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.kreitek.jhipster.domain.Album} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlbumDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @Lob
    private byte[] cover;

    private String coverContentType;
    private ArtistDTO artist;

    private StyleDTO style;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getCoverContentType() {
        return coverContentType;
    }

    public void setCoverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
    }

    public ArtistDTO getArtist() {
        return artist;
    }

    public void setArtist(ArtistDTO artist) {
        this.artist = artist;
    }

    public StyleDTO getStyle() {
        return style;
    }

    public void setStyle(StyleDTO style) {
        this.style = style;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlbumDTO)) {
            return false;
        }

        AlbumDTO albumDTO = (AlbumDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, albumDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlbumDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", cover='" + getCover() + "'" +
            ", artist=" + getArtist() +
            ", style=" + getStyle() +
            "}";
    }
}
