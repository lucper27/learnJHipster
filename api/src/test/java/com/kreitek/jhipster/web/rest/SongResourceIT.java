package com.kreitek.jhipster.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kreitek.jhipster.IntegrationTest;
import com.kreitek.jhipster.domain.Album;
import com.kreitek.jhipster.domain.Song;
import com.kreitek.jhipster.repository.SongRepository;
import com.kreitek.jhipster.service.SongService;
import com.kreitek.jhipster.service.criteria.SongCriteria;
import com.kreitek.jhipster.service.dto.SongDTO;
import com.kreitek.jhipster.service.mapper.SongMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SongResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SongResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 0;
    private static final Integer UPDATED_DURATION = 1;
    private static final Integer SMALLER_DURATION = 0 - 1;

    private static final LocalDate DEFAULT_INCLUSION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INCLUSION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_INCLUSION_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/songs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SongRepository songRepository;

    @Mock
    private SongRepository songRepositoryMock;

    @Autowired
    private SongMapper songMapper;

    @Mock
    private SongService songServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSongMockMvc;

    private Song song;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Song createEntity(EntityManager em) {
        Song song = new Song().title(DEFAULT_TITLE).duration(DEFAULT_DURATION).inclusionDate(DEFAULT_INCLUSION_DATE);
        // Add required entity
        Album album;
        if (TestUtil.findAll(em, Album.class).isEmpty()) {
            album = AlbumResourceIT.createEntity(em);
            em.persist(album);
            em.flush();
        } else {
            album = TestUtil.findAll(em, Album.class).get(0);
        }
        song.setAlbum(album);
        return song;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Song createUpdatedEntity(EntityManager em) {
        Song song = new Song().title(UPDATED_TITLE).duration(UPDATED_DURATION).inclusionDate(UPDATED_INCLUSION_DATE);
        // Add required entity
        Album album;
        if (TestUtil.findAll(em, Album.class).isEmpty()) {
            album = AlbumResourceIT.createUpdatedEntity(em);
            em.persist(album);
            em.flush();
        } else {
            album = TestUtil.findAll(em, Album.class).get(0);
        }
        song.setAlbum(album);
        return song;
    }

    @BeforeEach
    public void initTest() {
        song = createEntity(em);
    }

    @Test
    @Transactional
    void createSong() throws Exception {
        int databaseSizeBeforeCreate = songRepository.findAll().size();
        // Create the Song
        SongDTO songDTO = songMapper.toDto(song);
        restSongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(songDTO)))
            .andExpect(status().isCreated());

        // Validate the Song in the database
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeCreate + 1);
        Song testSong = songList.get(songList.size() - 1);
        assertThat(testSong.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSong.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testSong.getInclusionDate()).isEqualTo(DEFAULT_INCLUSION_DATE);
    }

    @Test
    @Transactional
    void createSongWithExistingId() throws Exception {
        // Create the Song with an existing ID
        song.setId(1L);
        SongDTO songDTO = songMapper.toDto(song);

        int databaseSizeBeforeCreate = songRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(songDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Song in the database
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = songRepository.findAll().size();
        // set the field null
        song.setTitle(null);

        // Create the Song, which fails.
        SongDTO songDTO = songMapper.toDto(song);

        restSongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(songDTO)))
            .andExpect(status().isBadRequest());

        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = songRepository.findAll().size();
        // set the field null
        song.setDuration(null);

        // Create the Song, which fails.
        SongDTO songDTO = songMapper.toDto(song);

        restSongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(songDTO)))
            .andExpect(status().isBadRequest());

        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInclusionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = songRepository.findAll().size();
        // set the field null
        song.setInclusionDate(null);

        // Create the Song, which fails.
        SongDTO songDTO = songMapper.toDto(song);

        restSongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(songDTO)))
            .andExpect(status().isBadRequest());

        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSongs() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList
        restSongMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(song.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].inclusionDate").value(hasItem(DEFAULT_INCLUSION_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSongsWithEagerRelationshipsIsEnabled() throws Exception {
        when(songServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSongMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(songServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSongsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(songServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSongMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(songRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSong() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get the song
        restSongMockMvc
            .perform(get(ENTITY_API_URL_ID, song.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(song.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.inclusionDate").value(DEFAULT_INCLUSION_DATE.toString()));
    }

    @Test
    @Transactional
    void getSongsByIdFiltering() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        Long id = song.getId();

        defaultSongShouldBeFound("id.equals=" + id);
        defaultSongShouldNotBeFound("id.notEquals=" + id);

        defaultSongShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSongShouldNotBeFound("id.greaterThan=" + id);

        defaultSongShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSongShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSongsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where title equals to DEFAULT_TITLE
        defaultSongShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the songList where title equals to UPDATED_TITLE
        defaultSongShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSongsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultSongShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the songList where title equals to UPDATED_TITLE
        defaultSongShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSongsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where title is not null
        defaultSongShouldBeFound("title.specified=true");

        // Get all the songList where title is null
        defaultSongShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllSongsByTitleContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where title contains DEFAULT_TITLE
        defaultSongShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the songList where title contains UPDATED_TITLE
        defaultSongShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSongsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where title does not contain DEFAULT_TITLE
        defaultSongShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the songList where title does not contain UPDATED_TITLE
        defaultSongShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSongsByDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where duration equals to DEFAULT_DURATION
        defaultSongShouldBeFound("duration.equals=" + DEFAULT_DURATION);

        // Get all the songList where duration equals to UPDATED_DURATION
        defaultSongShouldNotBeFound("duration.equals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllSongsByDurationIsInShouldWork() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where duration in DEFAULT_DURATION or UPDATED_DURATION
        defaultSongShouldBeFound("duration.in=" + DEFAULT_DURATION + "," + UPDATED_DURATION);

        // Get all the songList where duration equals to UPDATED_DURATION
        defaultSongShouldNotBeFound("duration.in=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllSongsByDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where duration is not null
        defaultSongShouldBeFound("duration.specified=true");

        // Get all the songList where duration is null
        defaultSongShouldNotBeFound("duration.specified=false");
    }

    @Test
    @Transactional
    void getAllSongsByDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where duration is greater than or equal to DEFAULT_DURATION
        defaultSongShouldBeFound("duration.greaterThanOrEqual=" + DEFAULT_DURATION);

        // Get all the songList where duration is greater than or equal to UPDATED_DURATION
        defaultSongShouldNotBeFound("duration.greaterThanOrEqual=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllSongsByDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where duration is less than or equal to DEFAULT_DURATION
        defaultSongShouldBeFound("duration.lessThanOrEqual=" + DEFAULT_DURATION);

        // Get all the songList where duration is less than or equal to SMALLER_DURATION
        defaultSongShouldNotBeFound("duration.lessThanOrEqual=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllSongsByDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where duration is less than DEFAULT_DURATION
        defaultSongShouldNotBeFound("duration.lessThan=" + DEFAULT_DURATION);

        // Get all the songList where duration is less than UPDATED_DURATION
        defaultSongShouldBeFound("duration.lessThan=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllSongsByDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where duration is greater than DEFAULT_DURATION
        defaultSongShouldNotBeFound("duration.greaterThan=" + DEFAULT_DURATION);

        // Get all the songList where duration is greater than SMALLER_DURATION
        defaultSongShouldBeFound("duration.greaterThan=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllSongsByInclusionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where inclusionDate equals to DEFAULT_INCLUSION_DATE
        defaultSongShouldBeFound("inclusionDate.equals=" + DEFAULT_INCLUSION_DATE);

        // Get all the songList where inclusionDate equals to UPDATED_INCLUSION_DATE
        defaultSongShouldNotBeFound("inclusionDate.equals=" + UPDATED_INCLUSION_DATE);
    }

    @Test
    @Transactional
    void getAllSongsByInclusionDateIsInShouldWork() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where inclusionDate in DEFAULT_INCLUSION_DATE or UPDATED_INCLUSION_DATE
        defaultSongShouldBeFound("inclusionDate.in=" + DEFAULT_INCLUSION_DATE + "," + UPDATED_INCLUSION_DATE);

        // Get all the songList where inclusionDate equals to UPDATED_INCLUSION_DATE
        defaultSongShouldNotBeFound("inclusionDate.in=" + UPDATED_INCLUSION_DATE);
    }

    @Test
    @Transactional
    void getAllSongsByInclusionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where inclusionDate is not null
        defaultSongShouldBeFound("inclusionDate.specified=true");

        // Get all the songList where inclusionDate is null
        defaultSongShouldNotBeFound("inclusionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSongsByInclusionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where inclusionDate is greater than or equal to DEFAULT_INCLUSION_DATE
        defaultSongShouldBeFound("inclusionDate.greaterThanOrEqual=" + DEFAULT_INCLUSION_DATE);

        // Get all the songList where inclusionDate is greater than or equal to UPDATED_INCLUSION_DATE
        defaultSongShouldNotBeFound("inclusionDate.greaterThanOrEqual=" + UPDATED_INCLUSION_DATE);
    }

    @Test
    @Transactional
    void getAllSongsByInclusionDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where inclusionDate is less than or equal to DEFAULT_INCLUSION_DATE
        defaultSongShouldBeFound("inclusionDate.lessThanOrEqual=" + DEFAULT_INCLUSION_DATE);

        // Get all the songList where inclusionDate is less than or equal to SMALLER_INCLUSION_DATE
        defaultSongShouldNotBeFound("inclusionDate.lessThanOrEqual=" + SMALLER_INCLUSION_DATE);
    }

    @Test
    @Transactional
    void getAllSongsByInclusionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where inclusionDate is less than DEFAULT_INCLUSION_DATE
        defaultSongShouldNotBeFound("inclusionDate.lessThan=" + DEFAULT_INCLUSION_DATE);

        // Get all the songList where inclusionDate is less than UPDATED_INCLUSION_DATE
        defaultSongShouldBeFound("inclusionDate.lessThan=" + UPDATED_INCLUSION_DATE);
    }

    @Test
    @Transactional
    void getAllSongsByInclusionDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        // Get all the songList where inclusionDate is greater than DEFAULT_INCLUSION_DATE
        defaultSongShouldNotBeFound("inclusionDate.greaterThan=" + DEFAULT_INCLUSION_DATE);

        // Get all the songList where inclusionDate is greater than SMALLER_INCLUSION_DATE
        defaultSongShouldBeFound("inclusionDate.greaterThan=" + SMALLER_INCLUSION_DATE);
    }

    @Test
    @Transactional
    void getAllSongsByAlbumIsEqualToSomething() throws Exception {
        Album album;
        if (TestUtil.findAll(em, Album.class).isEmpty()) {
            songRepository.saveAndFlush(song);
            album = AlbumResourceIT.createEntity(em);
        } else {
            album = TestUtil.findAll(em, Album.class).get(0);
        }
        em.persist(album);
        em.flush();
        song.setAlbum(album);
        songRepository.saveAndFlush(song);
        Long albumId = album.getId();

        // Get all the songList where album equals to albumId
        defaultSongShouldBeFound("albumId.equals=" + albumId);

        // Get all the songList where album equals to (albumId + 1)
        defaultSongShouldNotBeFound("albumId.equals=" + (albumId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSongShouldBeFound(String filter) throws Exception {
        restSongMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(song.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].inclusionDate").value(hasItem(DEFAULT_INCLUSION_DATE.toString())));

        // Check, that the count call also returns 1
        restSongMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSongShouldNotBeFound(String filter) throws Exception {
        restSongMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSongMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSong() throws Exception {
        // Get the song
        restSongMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSong() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        int databaseSizeBeforeUpdate = songRepository.findAll().size();

        // Update the song
        Song updatedSong = songRepository.findById(song.getId()).get();
        // Disconnect from session so that the updates on updatedSong are not directly saved in db
        em.detach(updatedSong);
        updatedSong.title(UPDATED_TITLE).duration(UPDATED_DURATION).inclusionDate(UPDATED_INCLUSION_DATE);
        SongDTO songDTO = songMapper.toDto(updatedSong);

        restSongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, songDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(songDTO))
            )
            .andExpect(status().isOk());

        // Validate the Song in the database
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeUpdate);
        Song testSong = songList.get(songList.size() - 1);
        assertThat(testSong.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSong.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testSong.getInclusionDate()).isEqualTo(UPDATED_INCLUSION_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSong() throws Exception {
        int databaseSizeBeforeUpdate = songRepository.findAll().size();
        song.setId(count.incrementAndGet());

        // Create the Song
        SongDTO songDTO = songMapper.toDto(song);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, songDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(songDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Song in the database
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSong() throws Exception {
        int databaseSizeBeforeUpdate = songRepository.findAll().size();
        song.setId(count.incrementAndGet());

        // Create the Song
        SongDTO songDTO = songMapper.toDto(song);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(songDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Song in the database
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSong() throws Exception {
        int databaseSizeBeforeUpdate = songRepository.findAll().size();
        song.setId(count.incrementAndGet());

        // Create the Song
        SongDTO songDTO = songMapper.toDto(song);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSongMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(songDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Song in the database
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSongWithPatch() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        int databaseSizeBeforeUpdate = songRepository.findAll().size();

        // Update the song using partial update
        Song partialUpdatedSong = new Song();
        partialUpdatedSong.setId(song.getId());

        partialUpdatedSong.title(UPDATED_TITLE).duration(UPDATED_DURATION).inclusionDate(UPDATED_INCLUSION_DATE);

        restSongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSong.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSong))
            )
            .andExpect(status().isOk());

        // Validate the Song in the database
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeUpdate);
        Song testSong = songList.get(songList.size() - 1);
        assertThat(testSong.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSong.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testSong.getInclusionDate()).isEqualTo(UPDATED_INCLUSION_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSongWithPatch() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        int databaseSizeBeforeUpdate = songRepository.findAll().size();

        // Update the song using partial update
        Song partialUpdatedSong = new Song();
        partialUpdatedSong.setId(song.getId());

        partialUpdatedSong.title(UPDATED_TITLE).duration(UPDATED_DURATION).inclusionDate(UPDATED_INCLUSION_DATE);

        restSongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSong.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSong))
            )
            .andExpect(status().isOk());

        // Validate the Song in the database
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeUpdate);
        Song testSong = songList.get(songList.size() - 1);
        assertThat(testSong.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSong.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testSong.getInclusionDate()).isEqualTo(UPDATED_INCLUSION_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSong() throws Exception {
        int databaseSizeBeforeUpdate = songRepository.findAll().size();
        song.setId(count.incrementAndGet());

        // Create the Song
        SongDTO songDTO = songMapper.toDto(song);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, songDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(songDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Song in the database
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSong() throws Exception {
        int databaseSizeBeforeUpdate = songRepository.findAll().size();
        song.setId(count.incrementAndGet());

        // Create the Song
        SongDTO songDTO = songMapper.toDto(song);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(songDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Song in the database
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSong() throws Exception {
        int databaseSizeBeforeUpdate = songRepository.findAll().size();
        song.setId(count.incrementAndGet());

        // Create the Song
        SongDTO songDTO = songMapper.toDto(song);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSongMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(songDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Song in the database
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSong() throws Exception {
        // Initialize the database
        songRepository.saveAndFlush(song);

        int databaseSizeBeforeDelete = songRepository.findAll().size();

        // Delete the song
        restSongMockMvc
            .perform(delete(ENTITY_API_URL_ID, song.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Song> songList = songRepository.findAll();
        assertThat(songList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
