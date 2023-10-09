package com.kreitek.jhipster.service.dto;

import com.kreitek.jhipster.domain.Artist;
import com.kreitek.jhipster.domain.Song;
import com.kreitek.jhipster.domain.Style;

import java.util.HashSet;
import java.util.Set;

public class AlbumFacadeDTO {

    private String title;

    private byte[] cover;

    private String coverContentType;

    private ArtistDTO artist;

    private StyleDTO style;

    private Set<SongDTO> songs = new HashSet<>();

    public AlbumFacadeDTO() {
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

    public Set<SongDTO> getSongs() {
        return songs;
    }

    public void setSongs(Set<SongDTO> songs) {
        this.songs = songs;
    }
}
