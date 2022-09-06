package com.kreitek.jhipster.service;

import com.kreitek.jhipster.domain.*; // for static metamodels
import com.kreitek.jhipster.domain.Artist;
import com.kreitek.jhipster.repository.ArtistRepository;
import com.kreitek.jhipster.service.criteria.ArtistCriteria;
import com.kreitek.jhipster.service.dto.ArtistDTO;
import com.kreitek.jhipster.service.mapper.ArtistMapper;
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
 * Service for executing complex queries for {@link Artist} entities in the database.
 * The main input is a {@link ArtistCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ArtistDTO} or a {@link Page} of {@link ArtistDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ArtistQueryService extends QueryService<Artist> {

    private final Logger log = LoggerFactory.getLogger(ArtistQueryService.class);

    private final ArtistRepository artistRepository;

    private final ArtistMapper artistMapper;

    public ArtistQueryService(ArtistRepository artistRepository, ArtistMapper artistMapper) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
    }

    /**
     * Return a {@link List} of {@link ArtistDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ArtistDTO> findByCriteria(ArtistCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Artist> specification = createSpecification(criteria);
        return artistMapper.toDto(artistRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ArtistDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ArtistDTO> findByCriteria(ArtistCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Artist> specification = createSpecification(criteria);
        return artistRepository.findAll(specification, page).map(artistMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ArtistCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Artist> specification = createSpecification(criteria);
        return artistRepository.count(specification);
    }

    /**
     * Function to convert {@link ArtistCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Artist> createSpecification(ArtistCriteria criteria) {
        Specification<Artist> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Artist_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Artist_.name));
            }
        }
        return specification;
    }
}
