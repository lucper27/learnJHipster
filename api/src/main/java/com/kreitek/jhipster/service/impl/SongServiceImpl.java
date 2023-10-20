package com.kreitek.jhipster.service.impl;

import com.kreitek.jhipster.domain.Song;
import com.kreitek.jhipster.repository.SongRepository;
import com.kreitek.jhipster.service.SongService;
import com.kreitek.jhipster.service.dto.AlbumDTO;
import com.kreitek.jhipster.service.dto.AlbumFacadeDTO;
import com.kreitek.jhipster.service.dto.ArtistDTO;
import com.kreitek.jhipster.service.dto.SongDTO;
import com.kreitek.jhipster.service.mapper.SongMapper;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Song}.
 */
@Service
@Transactional
public class SongServiceImpl implements SongService {

    private final Logger log = LoggerFactory.getLogger(SongServiceImpl.class);

    private final SongRepository songRepository;

    private final SongMapper songMapper;

    public SongServiceImpl(SongRepository songRepository, SongMapper songMapper) {
        this.songRepository = songRepository;
        this.songMapper = songMapper;
    }

    @Override
    public SongDTO save(SongDTO songDTO) {
        log.debug("Request to save Song : {}", songDTO);
        Song song = songMapper.toEntity(songDTO);
        song = songRepository.save(song);
        return songMapper.toDto(song);
    }

    @Override
    public SongDTO update(SongDTO songDTO) {
        log.debug("Request to update Song : {}", songDTO);
        Song song = songMapper.toEntity(songDTO);
        song = songRepository.save(song);
        return songMapper.toDto(song);
    }

    @Override
    public Optional<SongDTO> partialUpdate(SongDTO songDTO) {
        log.debug("Request to partially update Song : {}", songDTO);

        return songRepository
            .findById(songDTO.getId())
            .map(existingSong -> {
                songMapper.partialUpdate(existingSong, songDTO);

                return existingSong;
            })
            .map(songRepository::save)
            .map(songMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SongDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Songs");
        return songRepository.findAll(pageable).map(songMapper::toDto);
    }

    public Page<SongDTO> findAllWithEagerRelationships(Pageable pageable) {
        return songRepository.findAllWithEagerRelationships(pageable).map(songMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SongDTO> findOne(Long id) {
        log.debug("Request to get Song : {}", id);
        return songRepository.findOneWithEagerRelationships(id).map(songMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Song : {}", id);
        songRepository.deleteById(id);
    }

    @Override
    public Set<SongDTO> addSongsToAlbum(AlbumFacadeDTO albumFacadeDTO, AlbumDTO newAlbumDTO) {
        Set<SongDTO> songResult = new HashSet<>();
        AlbumDTO finalAlbumDTO = newAlbumDTO;
        ArtistDTO finalArtistDTO = albumFacadeDTO.getArtist();
        albumFacadeDTO.getSongs().forEach(eachSong -> {
            eachSong.setArtist(finalArtistDTO);
            eachSong.setAlbum(finalAlbumDTO);
            songResult.add(save(eachSong));
        });

        return songResult;
    }
}
