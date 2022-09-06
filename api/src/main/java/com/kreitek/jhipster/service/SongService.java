package com.kreitek.jhipster.service;

import com.kreitek.jhipster.service.dto.SongDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.kreitek.jhipster.domain.Song}.
 */
public interface SongService {
    /**
     * Save a song.
     *
     * @param songDTO the entity to save.
     * @return the persisted entity.
     */
    SongDTO save(SongDTO songDTO);

    /**
     * Updates a song.
     *
     * @param songDTO the entity to update.
     * @return the persisted entity.
     */
    SongDTO update(SongDTO songDTO);

    /**
     * Partially updates a song.
     *
     * @param songDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SongDTO> partialUpdate(SongDTO songDTO);

    /**
     * Get all the songs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SongDTO> findAll(Pageable pageable);

    /**
     * Get all the songs with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SongDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" song.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SongDTO> findOne(Long id);

    /**
     * Delete the "id" song.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
