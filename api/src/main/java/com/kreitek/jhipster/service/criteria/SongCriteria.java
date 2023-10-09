package com.kreitek.jhipster.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.kreitek.jhipster.domain.Song} entity. This class is used
 * in {@link com.kreitek.jhipster.web.rest.SongResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /songs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SongCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private IntegerFilter duration;

    private LocalDateFilter inclusionDate;

    private LongFilter albumId;

    private LongFilter artistId;

    private Boolean distinct;

    public SongCriteria() {}

    public SongCriteria(SongCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.duration = other.duration == null ? null : other.duration.copy();
        this.inclusionDate = other.inclusionDate == null ? null : other.inclusionDate.copy();
        this.albumId = other.albumId == null ? null : other.albumId.copy();
        this.artistId = other.artistId == null ? null : other.artistId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SongCriteria copy() {
        return new SongCriteria(this);
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

    public IntegerFilter getDuration() {
        return duration;
    }

    public IntegerFilter duration() {
        if (duration == null) {
            duration = new IntegerFilter();
        }
        return duration;
    }

    public void setDuration(IntegerFilter duration) {
        this.duration = duration;
    }

    public LocalDateFilter getInclusionDate() {
        return inclusionDate;
    }

    public LocalDateFilter inclusionDate() {
        if (inclusionDate == null) {
            inclusionDate = new LocalDateFilter();
        }
        return inclusionDate;
    }

    public void setInclusionDate(LocalDateFilter inclusionDate) {
        this.inclusionDate = inclusionDate;
    }

    public LongFilter getAlbumId() {
        return albumId;
    }

    public LongFilter albumId() {
        if (albumId == null) {
            albumId = new LongFilter();
        }
        return albumId;
    }

    public LongFilter artistId() {
        if (artistId == null) {
            artistId = new LongFilter();
        }
        return artistId;
    }

    public void setAlbumId(LongFilter albumId) {
        this.albumId = albumId;
    }

    public LongFilter getArtistId() {
        return artistId;
    }

    public void setArtistId(LongFilter artistId) {
        this.artistId = artistId;
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
        final SongCriteria that = (SongCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(inclusionDate, that.inclusionDate) &&
            Objects.equals(albumId, that.albumId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, duration, inclusionDate, albumId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SongCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (duration != null ? "duration=" + duration + ", " : "") +
            (inclusionDate != null ? "inclusionDate=" + inclusionDate + ", " : "") +
            (albumId != null ? "albumId=" + albumId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
