package com.kreitek.jhipster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kreitek.jhipster.IntegrationTest;
import com.kreitek.jhipster.domain.Artist;
import com.kreitek.jhipster.repository.ArtistRepository;
import com.kreitek.jhipster.service.criteria.ArtistCriteria;
import com.kreitek.jhipster.service.dto.ArtistDTO;
import com.kreitek.jhipster.service.mapper.ArtistMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ArtistResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtistResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/artists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtistMapper artistMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtistMockMvc;

    private Artist artist;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artist createEntity(EntityManager em) {
        Artist artist = new Artist().name(DEFAULT_NAME).image(DEFAULT_IMAGE).imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return artist;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artist createUpdatedEntity(EntityManager em) {
        Artist artist = new Artist().name(UPDATED_NAME).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return artist;
    }

    @BeforeEach
    public void initTest() {
        artist = createEntity(em);
    }

    @Test
    @Transactional
    void createArtist() throws Exception {
        int databaseSizeBeforeCreate = artistRepository.findAll().size();
        // Create the Artist
        ArtistDTO artistDTO = artistMapper.toDto(artist);
        restArtistMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artistDTO)))
            .andExpect(status().isCreated());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeCreate + 1);
        Artist testArtist = artistList.get(artistList.size() - 1);
        assertThat(testArtist.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArtist.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testArtist.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createArtistWithExistingId() throws Exception {
        // Create the Artist with an existing ID
        artist.setId(1L);
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        int databaseSizeBeforeCreate = artistRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtistMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = artistRepository.findAll().size();
        // set the field null
        artist.setName(null);

        // Create the Artist, which fails.
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        restArtistMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artistDTO)))
            .andExpect(status().isBadRequest());

        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArtists() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        // Get all the artistList
        restArtistMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artist.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getArtist() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        // Get the artist
        restArtistMockMvc
            .perform(get(ENTITY_API_URL_ID, artist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artist.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getArtistsByIdFiltering() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        Long id = artist.getId();

        defaultArtistShouldBeFound("id.equals=" + id);
        defaultArtistShouldNotBeFound("id.notEquals=" + id);

        defaultArtistShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultArtistShouldNotBeFound("id.greaterThan=" + id);

        defaultArtistShouldBeFound("id.lessThanOrEqual=" + id);
        defaultArtistShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllArtistsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        // Get all the artistList where name equals to DEFAULT_NAME
        defaultArtistShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the artistList where name equals to UPDATED_NAME
        defaultArtistShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllArtistsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        // Get all the artistList where name in DEFAULT_NAME or UPDATED_NAME
        defaultArtistShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the artistList where name equals to UPDATED_NAME
        defaultArtistShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllArtistsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        // Get all the artistList where name is not null
        defaultArtistShouldBeFound("name.specified=true");

        // Get all the artistList where name is null
        defaultArtistShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistsByNameContainsSomething() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        // Get all the artistList where name contains DEFAULT_NAME
        defaultArtistShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the artistList where name contains UPDATED_NAME
        defaultArtistShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllArtistsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        // Get all the artistList where name does not contain DEFAULT_NAME
        defaultArtistShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the artistList where name does not contain UPDATED_NAME
        defaultArtistShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultArtistShouldBeFound(String filter) throws Exception {
        restArtistMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artist.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));

        // Check, that the count call also returns 1
        restArtistMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultArtistShouldNotBeFound(String filter) throws Exception {
        restArtistMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restArtistMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingArtist() throws Exception {
        // Get the artist
        restArtistMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtist() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        int databaseSizeBeforeUpdate = artistRepository.findAll().size();

        // Update the artist
        Artist updatedArtist = artistRepository.findById(artist.getId()).get();
        // Disconnect from session so that the updates on updatedArtist are not directly saved in db
        em.detach(updatedArtist);
        updatedArtist.name(UPDATED_NAME).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        ArtistDTO artistDTO = artistMapper.toDto(updatedArtist);

        restArtistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artistDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistDTO))
            )
            .andExpect(status().isOk());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeUpdate);
        Artist testArtist = artistList.get(artistList.size() - 1);
        assertThat(testArtist.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArtist.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testArtist.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingArtist() throws Exception {
        int databaseSizeBeforeUpdate = artistRepository.findAll().size();
        artist.setId(count.incrementAndGet());

        // Create the Artist
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artistDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtist() throws Exception {
        int databaseSizeBeforeUpdate = artistRepository.findAll().size();
        artist.setId(count.incrementAndGet());

        // Create the Artist
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtist() throws Exception {
        int databaseSizeBeforeUpdate = artistRepository.findAll().size();
        artist.setId(count.incrementAndGet());

        // Create the Artist
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artistDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtistWithPatch() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        int databaseSizeBeforeUpdate = artistRepository.findAll().size();

        // Update the artist using partial update
        Artist partialUpdatedArtist = new Artist();
        partialUpdatedArtist.setId(artist.getId());

        partialUpdatedArtist.image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restArtistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtist.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtist))
            )
            .andExpect(status().isOk());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeUpdate);
        Artist testArtist = artistList.get(artistList.size() - 1);
        assertThat(testArtist.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArtist.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testArtist.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateArtistWithPatch() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        int databaseSizeBeforeUpdate = artistRepository.findAll().size();

        // Update the artist using partial update
        Artist partialUpdatedArtist = new Artist();
        partialUpdatedArtist.setId(artist.getId());

        partialUpdatedArtist.name(UPDATED_NAME).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restArtistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtist.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtist))
            )
            .andExpect(status().isOk());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeUpdate);
        Artist testArtist = artistList.get(artistList.size() - 1);
        assertThat(testArtist.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArtist.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testArtist.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingArtist() throws Exception {
        int databaseSizeBeforeUpdate = artistRepository.findAll().size();
        artist.setId(count.incrementAndGet());

        // Create the Artist
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artistDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtist() throws Exception {
        int databaseSizeBeforeUpdate = artistRepository.findAll().size();
        artist.setId(count.incrementAndGet());

        // Create the Artist
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtist() throws Exception {
        int databaseSizeBeforeUpdate = artistRepository.findAll().size();
        artist.setId(count.incrementAndGet());

        // Create the Artist
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(artistDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Artist in the database
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtist() throws Exception {
        // Initialize the database
        artistRepository.saveAndFlush(artist);

        int databaseSizeBeforeDelete = artistRepository.findAll().size();

        // Delete the artist
        restArtistMockMvc
            .perform(delete(ENTITY_API_URL_ID, artist.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Artist> artistList = artistRepository.findAll();
        assertThat(artistList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
