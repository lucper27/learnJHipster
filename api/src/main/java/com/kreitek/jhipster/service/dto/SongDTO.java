package com.kreitek.jhipster.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.kreitek.jhipster.domain.Song} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SongDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Min(value = 0)
    private Integer duration;

    @NotNull
    private LocalDate inclusionDate;

    private AlbumDTO album;

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDate getInclusionDate() {
        return inclusionDate;
    }

    public void setInclusionDate(LocalDate inclusionDate) {
        this.inclusionDate = inclusionDate;
    }

    public AlbumDTO getAlbum() {
        return album;
    }

    public void setAlbum(AlbumDTO album) {
        this.album = album;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SongDTO)) {
            return false;
        }

        SongDTO songDTO = (SongDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, songDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SongDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", duration=" + getDuration() +
            ", inclusionDate='" + getInclusionDate() + "'" +
            ", album=" + getAlbum() +
            "}";
    }
}
