package com.kreitek.jhipster.service.impl;


import com.kreitek.jhipster.exception.DuplicatedAlbumException;
import com.kreitek.jhipster.service.*;
import com.kreitek.jhipster.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
@Component
public class AlbumFacadeServiceImpl implements AlbumFacadeService {

    private final Logger log = LoggerFactory.getLogger(AlbumFacadeServiceImpl.class);
    private final AlbumService albumService;

    private final ArtistService artistService;

    private final SongService songService;

    private final StyleService styleService;

    public AlbumFacadeServiceImpl(AlbumService albumService, ArtistService artistService, SongService songService, StyleService styleService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
        this.styleService = styleService;
    }

    @Override
    @Transactional
    public AlbumFacadeDTO createAlbum(AlbumFacadeDTO albumFacadeDTO) {
        log.info("Creating album from album facadeDTO, {}", albumFacadeDTO);
        AlbumFacadeDTO newAlbumFacadeDTO;

        newAlbumFacadeDTO = setArtist(albumFacadeDTO);

        boolean albumExists = albumService.albumExists(newAlbumFacadeDTO);

        if (!albumExists) {
            log.info("Album doesn't exists, it's new album");
            newAlbumFacadeDTO = setStyle(newAlbumFacadeDTO);

            AlbumDTO newAlbumDTO = albumService.createAlbumFromFacade(newAlbumFacadeDTO);
            if (newAlbumFacadeDTO.getSongs().size() > 0) {
                Set<SongDTO> songResult = songService.addSongsToAlbum(newAlbumFacadeDTO, newAlbumDTO);
                newAlbumFacadeDTO.setSongs(songResult);
            }
        } else {
            log.error("Album already exists");
            throw new DuplicatedAlbumException("Album can't be duplicated");
        }
        return newAlbumFacadeDTO;
    }

    private AlbumFacadeDTO setStyle(AlbumFacadeDTO albumFacadeDTO) {
        Optional<StyleDTO> styleDTOOptional = styleService.findOneByName(albumFacadeDTO.getStyle().getName());
        StyleDTO styleDTO;
        if (styleDTOOptional.isPresent()) {
            styleDTO = styleDTOOptional.get();
        } else {
            styleDTO = styleService.save(albumFacadeDTO.getStyle());
        }
        albumFacadeDTO.setStyle(styleDTO);
        return albumFacadeDTO;
    }

    private AlbumFacadeDTO setArtist(AlbumFacadeDTO albumFacadeDTO) {
        ArtistDTO artistDTO;
        Optional<ArtistDTO> optionalArtistDTO = artistService.findByName(albumFacadeDTO.getArtist().getName());
        if (optionalArtistDTO.isPresent()) {
            artistDTO = optionalArtistDTO.get();
        } else {
            artistDTO = artistService.save(albumFacadeDTO.getArtist());
        }
        albumFacadeDTO.setArtist(artistDTO);
        return albumFacadeDTO;
    }


}
