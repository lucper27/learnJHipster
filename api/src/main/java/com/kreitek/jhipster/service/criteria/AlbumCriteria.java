package com.kreitek.jhipster.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.kreitek.jhipster.domain.Album} entity. This class is used
 * in {@link com.kreitek.jhipster.web.rest.AlbumResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /albums?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlbumCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private LongFilter artistId;

    private LongFilter styleId;

    private LongFilter songsId;

    private Boolean distinct;

    public AlbumCriteria() {}

    public AlbumCriteria(AlbumCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.artistId = other.artistId == null ? null : other.artistId.copy();
        this.styleId = other.styleId == null ? null : other.styleId.copy();
        this.songsId = other.songsId == null ? null : other.songsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AlbumCriteria copy() {
        return new AlbumCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public LongFilter getArtistId() {
        return artistId;
    }

    public LongFilter artistId() {
        if (artistId == null) {
            artistId = new LongFilter();
        }
        return artistId;
    }

    public void setArtistId(LongFilter artistId) {
        this.artistId = artistId;
    }

    public LongFilter getStyleId() {
        return styleId;
    }

    public LongFilter styleId() {
        if (styleId == null) {
            styleId = new LongFilter();
        }
        return styleId;
    }

    public void setStyleId(LongFilter styleId) {
        this.styleId = styleId;
    }

    public LongFilter getSongsId() {
        return songsId;
    }

    public LongFilter songsId() {
        if (songsId == null) {
            songsId = new LongFilter();
        }
        return songsId;
    }

    public void setSongsId(LongFilter songsId) {
        this.songsId = songsId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AlbumCriteria that = (AlbumCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(artistId, that.artistId) &&
            Objects.equals(styleId, that.styleId) &&
            Objects.equals(songsId, that.songsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, artistId, styleId, songsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlbumCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (artistId != null ? "artistId=" + artistId + ", " : "") +
            (styleId != null ? "styleId=" + styleId + ", " : "") +
            (songsId != null ? "songsId=" + songsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
