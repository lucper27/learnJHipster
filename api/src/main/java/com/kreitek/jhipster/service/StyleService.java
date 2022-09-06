package com.kreitek.jhipster.service;

import com.kreitek.jhipster.service.dto.StyleDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.kreitek.jhipster.domain.Style}.
 */
public interface StyleService {
    /**
     * Save a style.
     *
     * @param styleDTO the entity to save.
     * @return the persisted entity.
     */
    StyleDTO save(StyleDTO styleDTO);

    /**
     * Updates a style.
     *
     * @param styleDTO the entity to update.
     * @return the persisted entity.
     */
    StyleDTO update(StyleDTO styleDTO);

    /**
     * Partially updates a style.
     *
     * @param styleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StyleDTO> partialUpdate(StyleDTO styleDTO);

    /**
     * Get all the styles.
     *
     * @return the list of entities.
     */
    List<StyleDTO> findAll();

    /**
     * Get the "id" style.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StyleDTO> findOne(Long id);

    /**
     * Delete the "id" style.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
