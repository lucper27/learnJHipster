package com.kreitek.jhipster.service.impl;

import com.kreitek.jhipster.domain.Album;
import com.kreitek.jhipster.repository.AlbumRepository;
import com.kreitek.jhipster.service.AlbumQueryService;
import com.kreitek.jhipster.service.AlbumService;
import com.kreitek.jhipster.service.criteria.AlbumCriteria;
import com.kreitek.jhipster.service.dto.AlbumDTO;
import com.kreitek.jhipster.service.dto.AlbumFacadeDTO;
import com.kreitek.jhipster.service.mapper.AlbumMapper;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Service Implementation for managing {@link Album}.
 */
@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    private final Logger log = LoggerFactory.getLogger(AlbumServiceImpl.class);

    private final AlbumRepository albumRepository;

    private final AlbumMapper albumMapper;

    private final AlbumQueryService albumQueryService;

    public AlbumServiceImpl(AlbumRepository albumRepository, AlbumMapper albumMapper, AlbumQueryService albumQueryService) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
        this.albumQueryService = albumQueryService;
    }

    @Override
    public AlbumDTO save(AlbumDTO albumDTO) {
        log.debug("Request to save Album : {}", albumDTO);
        Album album = albumMapper.toEntity(albumDTO);
        album = albumRepository.save(album);
        return albumMapper.toDto(album);
    }

    @Override
    public AlbumDTO update(AlbumDTO albumDTO) {
        log.debug("Request to update Album : {}", albumDTO);
        Album album = albumMapper.toEntity(albumDTO);
        album = albumRepository.save(album);
        return albumMapper.toDto(album);
    }

    @Override
    public Optional<AlbumDTO> partialUpdate(AlbumDTO albumDTO) {
        log.debug("Request to partially update Album : {}", albumDTO);

        return albumRepository
            .findById(albumDTO.getId())
            .map(existingAlbum -> {
                albumMapper.partialUpdate(existingAlbum, albumDTO);

                return existingAlbum;
            })
            .map(albumRepository::save)
            .map(albumMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlbumDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Albums");
        return albumRepository.findAll(pageable).map(albumMapper::toDto);
    }

    public Page<AlbumDTO> findAllWithEagerRelationships(Pageable pageable) {
        return albumRepository.findAllWithEagerRelationships(pageable).map(albumMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AlbumDTO> findOne(Long id) {
        log.debug("Request to get Album : {}", id);
        return albumRepository.findOneWithEagerRelationships(id).map(albumMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Album : {}", id);
        albumRepository.deleteById(id);
    }

    @Override
    public boolean albumExists(AlbumFacadeDTO albumFacadeDTO) {
        AlbumCriteria albumCriteria = new AlbumCriteria();
        LongFilter artistId = new LongFilter();
        artistId.setEquals(albumFacadeDTO.getArtist().getId());
        StringFilter albumTitle = new StringFilter();
        albumTitle.setEquals(albumFacadeDTO.getTitle());
        albumCriteria.setTitle(albumTitle);
        albumCriteria.setArtistId(artistId);
        List<AlbumDTO> albumDTOS = albumQueryService.findByCriteria(albumCriteria);
        if (albumDTOS.isEmpty()) {
            return true;
        } else {
            log.error("Album already exists with id -> " + albumDTOS.get(0).getId());
            //throw DuplicatedAlbumException
            return false;
        }
    }

    @Override
    public AlbumDTO createAlbumFromFacade(AlbumFacadeDTO albumFacadeDTO) {
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setArtist(albumFacadeDTO.getArtist());
        albumDTO.setTitle(albumFacadeDTO.getTitle());
        albumDTO.setCover(albumFacadeDTO.getCover());
        albumDTO.setCoverContentType(albumFacadeDTO.getCoverContentType());
        albumDTO.setStyle(albumFacadeDTO.getStyle());
        return save(albumDTO);
    }
}
