package com.kreitek.jhipster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Album.
 */
@Entity
@Table(name = "album")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "cover", nullable = false)
    private byte[] cover;

    @NotNull
    @Column(name = "cover_content_type", nullable = false)
    private String coverContentType;

    @ManyToOne
    private Artist artist;

    @ManyToOne
    private Style style;

    @OneToMany(mappedBy = "album")
    @JsonIgnoreProperties(value = { "album" }, allowSetters = true)
    private Set<Song> songs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Album id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Album title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getCover() {
        return this.cover;
    }

    public Album cover(byte[] cover) {
        this.setCover(cover);
        return this;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getCoverContentType() {
        return this.coverContentType;
    }

    public Album coverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
        return this;
    }

    public void setCoverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
    }

    public Artist getArtist() {
        return this.artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album artist(Artist artist) {
        this.setArtist(artist);
        return this;
    }

    public Style getStyle() {
        return this.style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Album style(Style style) {
        this.setStyle(style);
        return this;
    }

    public Set<Song> getSongs() {
        return this.songs;
    }

    public void setSongs(Set<Song> songs) {
        if (this.songs != null) {
            this.songs.forEach(i -> i.setAlbum(null));
        }
        if (songs != null) {
            songs.forEach(i -> i.setAlbum(this));
        }
        this.songs = songs;
    }

    public Album songs(Set<Song> songs) {
        this.setSongs(songs);
        return this;
    }

    public Album addSongs(Song song) {
        this.songs.add(song);
        song.setAlbum(this);
        return this;
    }

    public Album removeSongs(Song song) {
        this.songs.remove(song);
        song.setAlbum(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Album)) {
            return false;
        }
        return id != null && id.equals(((Album) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Album{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", cover='" + getCover() + "'" +
            ", coverContentType='" + getCoverContentType() + "'" +
            "}";
    }
}
