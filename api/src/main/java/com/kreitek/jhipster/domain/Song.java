package com.kreitek.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Song.
 */
@Entity
@Table(name = "song")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Min(value = 0)
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @NotNull
    @Column(name = "inclusion_date", nullable = false)
    private LocalDate inclusionDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "artist", "style", "songs" }, allowSetters = true)
    private Album album;

    @OneToOne
    @JoinColumn(unique = true)
    private Artist artist;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Song id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Song title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public Song duration(Integer duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDate getInclusionDate() {
        return this.inclusionDate;
    }

    public Song inclusionDate(LocalDate inclusionDate) {
        this.setInclusionDate(inclusionDate);
        return this;
    }

    public void setInclusionDate(LocalDate inclusionDate) {
        this.inclusionDate = inclusionDate;
    }

    public Album getAlbum() {
        return this.album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Song album(Album album) {
        this.setAlbum(album);
        return this;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Song)) {
            return false;
        }
        return id != null && id.equals(((Song) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Song{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", duration=" + getDuration() +
            ", inclusionDate='" + getInclusionDate() + "'" +
            "}";
    }
}
