package com.kreitek.jhipster.web.rest;

import com.kreitek.jhipster.repository.StyleRepository;
import com.kreitek.jhipster.service.StyleService;
import com.kreitek.jhipster.service.dto.StyleDTO;
import com.kreitek.jhipster.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.kreitek.jhipster.domain.Style}.
 */
@RestController
@RequestMapping("/api")
public class StyleResource {

    private final Logger log = LoggerFactory.getLogger(StyleResource.class);

    private static final String ENTITY_NAME = "style";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StyleService styleService;

    private final StyleRepository styleRepository;

    public StyleResource(StyleService styleService, StyleRepository styleRepository) {
        this.styleService = styleService;
        this.styleRepository = styleRepository;
    }

    /**
     * {@code POST  /styles} : Create a new style.
     *
     * @param styleDTO the styleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new styleDTO, or with status {@code 400 (Bad Request)} if the style has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/styles")
    public ResponseEntity<StyleDTO> createStyle(@Valid @RequestBody StyleDTO styleDTO) throws URISyntaxException {
        log.debug("REST request to save Style : {}", styleDTO);
        if (styleDTO.getId() != null) {
            throw new BadRequestAlertException("A new style cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StyleDTO result = styleService.save(styleDTO);
        return ResponseEntity
            .created(new URI("/api/styles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /styles/:id} : Updates an existing style.
     *
     * @param id the id of the styleDTO to save.
     * @param styleDTO the styleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated styleDTO,
     * or with status {@code 400 (Bad Request)} if the styleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the styleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/styles/{id}")
    public ResponseEntity<StyleDTO> updateStyle(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StyleDTO styleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Style : {}, {}", id, styleDTO);
        if (styleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, styleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!styleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StyleDTO result = styleService.update(styleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, styleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /styles/:id} : Partial updates given fields of an existing style, field will ignore if it is null
     *
     * @param id the id of the styleDTO to save.
     * @param styleDTO the styleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated styleDTO,
     * or with status {@code 400 (Bad Request)} if the styleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the styleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the styleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/styles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StyleDTO> partialUpdateStyle(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StyleDTO styleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Style partially : {}, {}", id, styleDTO);
        if (styleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, styleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!styleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StyleDTO> result = styleService.partialUpdate(styleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, styleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /styles} : get all the styles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of styles in body.
     */
    @GetMapping("/styles")
    public List<StyleDTO> getAllStyles() {
        log.debug("REST request to get all Styles");
        return styleService.findAll();
    }

    /**
     * {@code GET  /styles/:id} : get the "id" style.
     *
     * @param id the id of the styleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the styleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/styles/{id}")
    public ResponseEntity<StyleDTO> getStyle(@PathVariable Long id) {
        log.debug("REST request to get Style : {}", id);
        Optional<StyleDTO> styleDTO = styleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(styleDTO);
    }

    /**
     * {@code DELETE  /styles/:id} : delete the "id" style.
     *
     * @param id the id of the styleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/styles/{id}")
    public ResponseEntity<Void> deleteStyle(@PathVariable Long id) {
        log.debug("REST request to delete Style : {}", id);
        styleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
