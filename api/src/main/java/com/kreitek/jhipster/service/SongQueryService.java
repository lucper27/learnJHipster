package com.kreitek.jhipster.service;

import com.kreitek.jhipster.domain.*; // for static metamodels
import com.kreitek.jhipster.domain.Song;
import com.kreitek.jhipster.domain.song.SongCheckerFilterByAlbumAppliedGuard;
import com.kreitek.jhipster.repository.SongRepository;
import com.kreitek.jhipster.service.criteria.SongCriteria;
import com.kreitek.jhipster.service.dto.SongDTO;
import com.kreitek.jhipster.service.mapper.SongMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Song} entities in the database.
 * The main input is a {@link SongCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SongDTO} or a {@link Page} of {@link SongDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SongQueryService extends QueryService<Song> {

    private final Logger log = LoggerFactory.getLogger(SongQueryService.class);

    private final SongRepository songRepository;

    private final SongMapper songMapper;

    private final SongCheckerFilterByAlbumAppliedGuard songCheckerFilterByAlbumAppliedGuard;

    public SongQueryService(SongRepository songRepository, SongMapper songMapper, SongCheckerFilterByAlbumAppliedGuard songCheckerFilterByAlbumAppliedGuard) {
        this.songRepository = songRepository;
        this.songMapper = songMapper;
        this.songCheckerFilterByAlbumAppliedGuard = songCheckerFilterByAlbumAppliedGuard;
    }

    /**
     * Return a {@link List} of {@link SongDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SongDTO> findByCriteria(SongCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Song> specification = createSpecification(criteria);
        return songMapper.toDto(songRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SongDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SongDTO> findByCriteria(SongCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        this.songCheckerFilterByAlbumAppliedGuard.check(criteria);
        final Specification<Song> specification = createSpecification(criteria);
        return songRepository.findAll(specification, page).map(songMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SongCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Song> specification = createSpecification(criteria);
        return songRepository.count(specification);
    }

    /**
     * Function to convert {@link SongCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Song> createSpecification(SongCriteria criteria) {
        Specification<Song> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Song_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Song_.title));
            }
            if (criteria.getDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuration(), Song_.duration));
            }
            if (criteria.getInclusionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInclusionDate(), Song_.inclusionDate));
            }
            if (criteria.getAlbumId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAlbumId(), root -> root.join(Song_.album, JoinType.LEFT).get(Album_.id))
                    );
            }
            if (criteria.getArtistId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getArtistId(), root -> root.join(Song_.artist, JoinType.LEFT).get(Artist_.id))
                    );
            }
        }
        return specification;
    }
}
