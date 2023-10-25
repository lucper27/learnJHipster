package com.kreitek.jhipster.service.impl;

import com.kreitek.jhipster.domain.Artist;
import com.kreitek.jhipster.repository.ArtistRepository;
import com.kreitek.jhipster.service.ArtistQueryService;
import com.kreitek.jhipster.service.ArtistService;
import com.kreitek.jhipster.service.criteria.ArtistCriteria;
import com.kreitek.jhipster.service.dto.AlbumFacadeDTO;
import com.kreitek.jhipster.service.dto.ArtistDTO;
import com.kreitek.jhipster.service.mapper.ArtistMapper;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.StringFilter;

/**
 * Service Implementation for managing {@link Artist}.
 */
@Service
@Transactional
public class ArtistServiceImpl implements ArtistService {

    private final Logger log = LoggerFactory.getLogger(ArtistServiceImpl.class);

    private final ArtistRepository artistRepository;

    private final ArtistMapper artistMapper;

    private final ArtistQueryService artistQueryService;

    public ArtistServiceImpl(ArtistRepository artistRepository, ArtistMapper artistMapper, ArtistQueryService artistQueryService) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
        this.artistQueryService = artistQueryService;
    }

    @Override
    public ArtistDTO save(ArtistDTO artistDTO) {
        log.debug("Request to save Artist : {}", artistDTO);
        Artist artist = artistMapper.toEntity(artistDTO);
        artist = artistRepository.save(artist);
        return artistMapper.toDto(artist);
    }

    @Override
    public ArtistDTO update(ArtistDTO artistDTO) {
        log.debug("Request to update Artist : {}", artistDTO);
        Artist artist = artistMapper.toEntity(artistDTO);
        artist = artistRepository.save(artist);
        return artistMapper.toDto(artist);
    }

    @Override
    public Optional<ArtistDTO> partialUpdate(ArtistDTO artistDTO) {
        log.debug("Request to partially update Artist : {}", artistDTO);

        return artistRepository
            .findById(artistDTO.getId())
            .map(existingArtist -> {
                artistMapper.partialUpdate(existingArtist, artistDTO);

                return existingArtist;
            })
            .map(artistRepository::save)
            .map(artistMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Artists");
        return artistRepository.findAll(pageable).map(artistMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtistDTO> findOne(Long id) {
        log.debug("Request to get Artist : {}", id);
        return artistRepository.findById(id).map(artistMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Artist : {}", id);
        artistRepository.deleteById(id);
    }

    @Override
    public boolean verifyArtistExistsByName(String name) {
        log.info("Verify if the artist exists -> {}", name);
        ArtistCriteria artistCriteria = new ArtistCriteria();
        StringFilter artistName = new StringFilter();
        artistName.setEquals(name);
        artistCriteria.setName(artistName);
        // el campo nombre es único en la bd, por lo que no pueden haber 2 artistas con el mismo nombre (al menos en esta bd ficticia)
        List<ArtistDTO> artistDTOS = artistQueryService.findByCriteria(artistCriteria);
        return artistDTOS.size() > 0;
    }

    @Override
    public Optional<ArtistDTO> findByName(String name) {
        return artistRepository.findByName(name).map(artistMapper::toDto);
    }


}
